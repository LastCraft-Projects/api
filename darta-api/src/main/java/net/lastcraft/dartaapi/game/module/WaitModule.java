package net.lastcraft.dartaapi.game.module;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.event.game.ChangeGameStateEvent;
import net.lastcraft.api.event.gamer.GamerChangePrefixEvent;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.dartaapi.boards.WaitingBoard;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.game.SettingsGameListener;
import net.lastcraft.dartaapi.game.perk.PerksGui;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.listeners.*;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WaitModule extends DListener implements Runnable {
    public static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private static boolean starting;
    private static AtomicInteger time;

    private Thread thread;
    private boolean startAdmin = false;

    protected List<DListener> listeners = new ArrayList<>();

    private static Map<String, SelectionTeam> selectionTeam = new ConcurrentHashMap<>();

    public static Map<Player, PerksGui> perkgui = new ConcurrentHashMap<>();

    @SuppressWarnings("deprecation")
    public WaitModule() {
        Bukkit.createWorld(WorldCreator.name("endlobby").generator("DartaAPI").generateStructures(false));
        time = new AtomicInteger();
        if (thread != null) {
            thread.stop();
            thread = null;
        }
        starting = false;

        listeners.add(new EntitySpawnListener());
        listeners.add(new ItemSpawnListener());
        listeners.add(new PhysicalListener());
        listeners.add(new WeatherListener());
        listeners.add(new BlockPhysicsListener());
        listeners.add(new SettingsGameListener());
        listeners.add(new DamageListener());
        listeners.add(new LobbyGuardListener());
        listeners.add(this);
    }

    public static boolean isStarting(){
        return starting;
    }

    public static int getTime() {
        return time.get();
    }

    @EventHandler
    public void onGamerJoinEvent(AsyncGamerJoinEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }
        new WaitingBoard(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent e) {
        int online = Bukkit.getOnlinePlayers().size();

        Player player = e.getPlayer();
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);

        gamer.setGameMode(GameModeType.ADVENTURE);

        if (!perkgui.containsKey(player)) {
            perkgui.put(player, new PerksGui(player));
        }

        if (GameSettings.teamMode) {
            player.getInventory().setItem(0, ItemsListener.getSelectionTeam(player));
            selectionTeam.put(player.getName(), new SelectionTeam(player));
        }

        alertMessageAll(true,"JOIN_GAMER_ARENA", player.getDisplayName(), String.valueOf(online), String.valueOf(GameSettings.slots));

        player.getInventory().setItem(5, ItemsListener.getPerk(player));
        //achievement.getInventory().setItem(3, ItemsListener.getKits(achievement));

        player.getInventory().setItem(8, ItemsListener.getHub(player));

        if (!starting) {
            if (GameSettings.toStart <= online) {
                this.start();
            }
        } else if (GameSettings.slots == online && time.get() > 10) {
            time.set(10);
        }

        player.teleport(GameSettings.lobbyLoc.clone());
        player.setExp(1.0f);
        player.setLevel(60);
    }

    @EventHandler
    public void onChangeInv(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.CRAFTING) {
            ItemStack currentItem = e.getCurrentItem();

            if (currentItem == null) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.CHEST) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.TRAPPED_CHEST) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.ENDER_CHEST) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.ANVIL) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.FURNACE) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.WORKBENCH) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.JUKEBOX) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.BED) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.DISPENSER) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.HOPPER) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.HOPPER_MINECART) ||
                (e.getAction() == Action.RIGHT_CLICK_BLOCK) && (e.getClickedBlock().getType() == Material.MINECART)) {
            e.setCancelled(true);
        }
        if ((e.getClickedBlock() instanceof Block) && (e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            int block = e.getClickedBlock().getTypeId();
            if (block == 26 || block == 355) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getLocation().getY() <= 0) {
            player.teleport(GameSettings.lobbyLoc);
        }
    }

    public static Map<String, SelectionTeam> getSelectionTeam() {
        return selectionTeam;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        int online = Bukkit.getOnlinePlayers().size() - 1;
        Player joinPlayer = e.getPlayer();

        selectionTeam.remove(joinPlayer.getName());
        SelectionTeam.resetSelectedTeams(joinPlayer);

        alertMessageAll(true, "QUIT_GAMER_ARENA", joinPlayer.getDisplayName(), String.valueOf(online), String.valueOf(GameSettings.slots));

        perkgui.remove(e.getPlayer());

        if (online < GameSettings.toStart && GameState.getCurrent() == GameState.STARTING) {
            if (online > 1 && startAdmin) {
                return;
            } else {
                startAdmin = false;
            }

            alertMessageAll(false, "ERROR_START_GAMER_ARENA");

            time.set(0);
            starting = false;
            thread.stop();
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.setExp(1.0f);
                player.setLevel(60);
            }

            GameState.setCurrent(GameState.WAITING);
            ChangeGameStateEvent changeGameStateEvent = new ChangeGameStateEvent(GameState.WAITING);
            BukkitUtil.callEvent(changeGameStateEvent);
        }
    }

    private void start() {
        GameState.setCurrent(GameState.STARTING);
        ChangeGameStateEvent changeGameStateEvent = new ChangeGameStateEvent(GameState.STARTING);
        BukkitUtil.callEvent(changeGameStateEvent);
        time.set(60);
        thread = new Thread(this, "StartTimer");
        thread.start();
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (!e.getMessage().equalsIgnoreCase("/start")) {
            return;
        }
        BukkitGamer gamer = GAMER_MANAGER.getGamer(e.getPlayer());
        if (gamer == null)
            return;
        if (!gamer.isAdmin())
            return;

        e.setCancelled(true);
        startAdmin = true;
        if (!starting)
            this.start();

        if (time.get() == 0 || time.get() > 20)
            time.set(20);

    }

    @EventHandler
    public void onChangePrefix(GamerChangePrefixEvent e) {
        if (GameSettings.teamMode)
            SelectionTeam.updateInventoryAll();
    }

    @Override
    public void run() {
        if (starting) {
            return;
        }
        starting = true;
        int i;
        try {
            while ((i = time.get()) > 0) {
                if (i % 30 == 0 || i <= 10 || i == 20)
                    for (GamerEntity gamerEntity : GAMER_MANAGER.getGamerEntities().values()) {
                        String message = gamerEntity.getLanguage().getMessage("START_GAME_ARENA", String.valueOf(i),
                                StringUtil.getCorrectWord(i, "TIME_SECOND_1", gamerEntity.getLanguage()));

                        if (gamerEntity instanceof BukkitGamer) {
                            ((BukkitGamer) gamerEntity).sendActionBar(message);
                        } else {
                            gamerEntity.sendMessage(message);
                        }
                    }


                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.setExp((float) i / 60.0f);
                    player.setLevel(i);
                    if (i > 10)
                        continue;
                    player.playSound(player.getLocation(), "random.orb", 1.0f, i * 0.1f + 0.4f);
                }
                Thread.sleep(1000);
                time.getAndDecrement();
            }
            BukkitUtil.runTask(() -> new StartModule(this));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage(boolean prefix, GamerEntity gamerEntity, String key, Object... objects) {
        Language lang = gamerEntity.getLanguage();
        gamerEntity.sendMessage((prefix ? GameSettings.prefix : "") + lang.getMessage(key, objects));
    }

    public static void alertMessageAll(boolean prefix, String key, Object... objects) {
        for (GamerEntity gamerEntity : GAMER_MANAGER.getGamerEntities().values()) {
            sendMessage(prefix, gamerEntity, key, objects);
        }
    }
}