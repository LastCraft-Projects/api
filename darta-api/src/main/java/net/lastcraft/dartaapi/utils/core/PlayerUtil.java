package net.lastcraft.dartaapi.utils.core;

import lombok.experimental.UtilityClass;
import net.lastcraft.api.BorderAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.TitleAPI;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.api.util.LocationUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.connector.Core;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
@Deprecated
public class PlayerUtil {

    private final NmsManager NMS_MANAGER = NmsAPI.getManager();
    private final BorderAPI BOARDER_API = LastCraft.getBorderAPI();
    private final TitleAPI TITLE_API = LastCraft.getTitlesAPI();
    private final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private final SoundAPI SOUND_API = LastCraft.getSoundAPI();

    @Deprecated
    public static Collection<Player> getAlivePlayers() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (isAlive(player))
                players.add(player);

        }
        return players;
    }

    @Deprecated
    public static Collection<Player> getSpectators() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player == null)
                continue;
            if (isSpectator(player))
                players.add(player);

        }
        return players;
    }

    @Deprecated
    public static Collection<Player> getGhosts() {
        Set<Player> players = new HashSet<>();
        for (String playerName : GamerAPI.getGamers().keySet()) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player == null)
                continue;
            if (isGhost(player))
                players.add(player);

        }
        return players;
    }

    public Collection<Player> getNearbyPlayers(Player player, int radius) {
        return player.getNearbyEntities(radius, radius, radius).stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toCollection(HashSet::new));
    }

    public Collection<Player> getNearbyPlayers(Location location, int radius){
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> LocationUtil.distance(player.getLocation(), location) <= radius
                        && LocationUtil.distance(player.getLocation(), location) != -1)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Deprecated
    public static boolean isAlive(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer != null){
                GameModeType gameModeType = gamer.getGameMode();
                return gameModeType != GameModeType.SPECTATOR;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isGhost(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer != null) {
                GameModeType gameModeType = gamer.getGameMode();
                return gameModeType == GameModeType.GHOST;
            }
        }
        return false;
    }

    @Deprecated
    public static boolean isSpectator(Player player) {
        if (player != null && player.isOnline()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer != null) {
                GameModeType gameModeType = gamer.getGameMode();
                return gameModeType == GameModeType.SPECTATOR;
            }
        }
        return false;
    }

    public boolean havePotionEffectType(Player player, PotionEffectType potionEffectType) {
        return player.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getType() == potionEffectType)
                .collect(Collectors.toList()).size() > 0;
    }

    public int getPotionEffectLevel(Player player, PotionEffectType potionEffectType) {
        PotionEffect effect = player.getActivePotionEffects().stream()
                .filter(potionEffect -> potionEffect.getType() == potionEffectType)
                .findFirst()
                .orElse(null);
        return effect != null ? effect.getAmplifier() : -1;
    }

    public void removePotionEffect(Player player, PotionEffectType potionEffectType) {
        player.removePotionEffect(potionEffectType);
    }

    public void addPotionEffect(Player player, PotionEffectType potionEffectType, int level){
        if (player == null) {
            return;
        }

        if (havePotionEffectType(player, potionEffectType))
            removePotionEffect(player, potionEffectType);

        player.addPotionEffect(new PotionEffect(potionEffectType, Integer.MAX_VALUE, level));
    }

    public void addPotionEffect(Player player, PotionEffectType potionEffectType, int level, int seconds){
        if (havePotionEffectType(player, potionEffectType))
            removePotionEffect(player, potionEffectType);

        player.addPotionEffect(new PotionEffect(potionEffectType, seconds*20, level));
    }

    public void reset(Player player) {
        if (player == null || !player.isOnline())
            return;

        try {
            player.getActivePotionEffects().forEach(potionEffect ->
                    player.removePotionEffect(potionEffect.getType()));

            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.setExp(0.0f);
            player.setLevel(0);
            player.setFireTicks(0);

            player.closeInventory();
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        } catch (Exception ignored) {}

        BukkitUtil.runTask(()-> {
            if (!player.isOnline())
                return;
            NMS_MANAGER.disableFire(player);
            NMS_MANAGER.removeArrowFromPlayer(player);
        });
    }

    public void setRespawn(Player player, int respawnTime, Runnable runnable) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;
        Language lang = gamer.getLanguage();
        reset(player);

        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));

        Bukkit.getOnlinePlayers().forEach(p -> p.hidePlayer(player));

        new BukkitRunnable(){

            String title = lang.getMessage( "DEATH_MSG_TITLE");
            String subTitle = lang.getMessage("RESPAWN_SUBTITLE", String.valueOf(respawnTime),
                    net.lastcraft.base.util.StringUtil.getCorrectWord(respawnTime, "TIME_SECOND_1", lang));

            int time = respawnTime * 20;
            @Override
            public void run() {
                if (!player.isOnline()){
                    cancel();
                    return;
                }
                if (GameState.END == GameState.getCurrent()){
                    Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));
                    cancel();
                    return;
                }
                BOARDER_API.sendRedScreen(player);
                player.setAllowFlight(true);
                player.setFlying(true);
                player.teleport(GameSettings.spectatorLoc);
                if (time == 0){
                    Bukkit.getOnlinePlayers().forEach(p -> p.showPlayer(player));

                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    title = lang.getMessage( "RESPAWN_TITLE");
                    TITLE_API.sendTitle(player, title, " ", 0, 2 * 20, 0);

                    BukkitUtil.runTask(runnable);
                    BukkitUtil.runTask(() -> NMS_MANAGER.disableFire(player));

                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.setFallDistance(0);
                    cancel();
                    return;
                }
                if (time % 20 == 0) {
                    subTitle = lang.getMessage("RESPAWN_SUBTITLE", String.valueOf(time / 20),
                            net.lastcraft.base.util.StringUtil.getCorrectWord(time / 20, "TIME_SECOND_1", lang));
                    TITLE_API.sendTitle(player, title, subTitle, 0, 2 * 20, 0);
                }
                time--;
            }
        }.runTaskTimer(DartaAPI.getInstance(), 5L, 1);

    }

    @Deprecated
    public static void redirectToHub(Player player) {
        Core.redirect(player, CoreUtil.getRandom(GameSettings.hubs));
    }

    @Deprecated
    public void death(Player death) {
        Player killer = death.getKiller();
        reset(death);
        if (killer == null)
            return;

        SOUND_API.play(killer, SoundType.POSITIVE);
    }
}
