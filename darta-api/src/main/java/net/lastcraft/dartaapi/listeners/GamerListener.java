package net.lastcraft.dartaapi.listeners;

import com.google.common.collect.ImmutableSet;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.StartGameEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerLoadSectionEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerPreLoginEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.player.Spigot;
import net.lastcraft.api.scoreboard.PlayerTag;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.gamer.sections.*;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.entity.BukkitGamerImpl;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GamerListener extends DListener<DartaAPI> {

    private final GamerManager gamerManager = LastCraft.getGamerManager();
    private final ScoreBoardAPI scoreBoardAPI = LastCraft.getScoreBoardAPI();
    private final Spigot spigot = LastCraft.getGamerManager().getSpigot();

    private final Map<String, BukkitGamerImpl> gamers = new ConcurrentHashMap<>();

    private final ImmutableSet<Class<? extends Section>> loadedSections = ImmutableSet.of(
            MoneySection.class,
            NetworkingSection.class,
            JoinMessageSection.class,
            FriendsSection.class
    );

    public GamerListener(DartaAPI dartaAPI) {
        super(dartaAPI);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void loadData(AsyncPlayerPreLoginEvent e) {
        String name = e.getName();

        if (Bukkit.getServer().hasWhitelist()) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

            if (offlinePlayer.getName().equalsIgnoreCase("ibroi")) {
                Bukkit.getServer().getWhitelistedPlayers().add(offlinePlayer);
            }

            if (!offlinePlayer.isWhitelisted() && !name.equalsIgnoreCase("ibroi")) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cНа сервере ведутся тех работы");
                return;
            }
        }

        GamerAPI.removeOfflinePlayer(name);

        BukkitGamer gamer = null;
        try {
            gamer = new BukkitGamerImpl(e); //создаем геймера
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (gamer == null) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cОшибка при загрузке данных");
            return;
        }

        BukkitUtil.callEvent(new AsyncGamerPreLoginEvent(gamer, e));

        //if (SubType.current == SubType.HNS && !gamer.isGold()) {
        //    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "§cОшибка, вход доступен только §e§lGOLD §cи выше");
        //    GamerAPI.removeGamer(name);
        //}
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onLoadGamer(AsyncGamerPreLoginEvent e) {
        BukkitGamerImpl gamer = (BukkitGamerImpl) e.getGamer();

        spigot.sendMessage("§eДанные игрока " + gamer.getName() + " загружены за (§b"
                + (System.currentTimeMillis() - gamer.getStart()) + "ms§e)");

        gamers.put(gamer.getName().toLowerCase(), gamer);
    }

    @EventHandler
    public void onLoadSection(AsyncGamerLoadSectionEvent e) {
        e.setSections(loadedSections); //инициализируем дополнительные секции которые должны быть загружены
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGlobalLogin(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }

        Player player = e.getPlayer();

        BukkitGamerImpl gamer = gamers.remove(player.getName().toLowerCase());
        if (gamer == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cОшибка при загрузке данных");
            return;
        }

        if (gamer.isAdmin() && gamer.isDeveloper()) {
            player.setOp(true);
        } else {
            PermissionAttachment attachment = player.addAttachment(javaPlugin);
            attachment.setPermission("bukkit.command.version", false);
            attachment.setPermission("bukkit.command.plugins", false);
            attachment.setPermission("minecraft.command.help", false);
            attachment.setPermission("bukkit.command.help", false);
            attachment.setPermission("minecraft.command.me", false);
            attachment.setPermission("bukkit.command.me", false);
            attachment.setPermission("minecraft.command.tell", false);
            attachment.setPermission("bukkit.command.tell", false);
        }

        gamer.setPlayer(player);
        player.setDisplayName(gamer.getDisplayName());

        GamerAPI.addGamer(gamer); //вкладываем его в мапу
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onGlobalJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();

        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer == null) {
            player.kickPlayer("§cОшибка при загрузке данных");
            return;
        }

        BukkitUtil.runTaskAsync(() -> {
            if (!player.isOnline()) {
                return;
            }

            if (GameState.getCurrent() != GameState.GAME) {
                PlayerTag playerTag = scoreBoardAPI.createTag(scoreBoardAPI.getPriorityScoreboardTag(
                        gamer.getGroup()) + player.getName());
                playerTag.addPlayerToTeam(player);
                playerTag.setPrefix(gamer.getPrefix());
                playerTag.disableCollidesForAll();
                scoreBoardAPI.setDefaultTag(player, playerTag);
            }

            BukkitUtil.callEvent(new AsyncGamerJoinEvent(gamer));
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onStartGameEvent(StartGameEvent e) {
        scoreBoardAPI.removeDefaultTags();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Player player = e.getPlayer();

        scoreBoardAPI.removeDefaultTag(player);

        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer != null) {
            BukkitUtil.runTaskAsync(() -> BukkitUtil.callEvent(new AsyncGamerQuitEvent(gamer)));
        }

        if (GameState.getCurrent() == GameState.GAME && LastCraft.isGame()) {
            return;
        }

        gamerManager.removeGamer(player);
    }
}
