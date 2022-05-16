package net.lastcraft.dartaapi.game.spectator;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class SPlayer {
    static final HashMap<String, SPlayer> sPlayers = new HashMap<>();

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public static void removeSPlayer(Player player) {
        sPlayers.remove(player.getName());
    }

    public static SPlayer getSPlayer(Player player) {
        if (sPlayers.get(player.getName()) == null) {
            return new SPlayer(player);
        }
        return sPlayers.get(player.getName());
    }

    private Player player;
    private int speedSpec;
    private int alwaysFly;
    private int hideSpectators;
    private int vision;
    private Player nearPlayer;
    private SpectatorSettings spectatorSettings;

    public void setNearPlayer(Player nearPlayer) {
        this.nearPlayer = nearPlayer;
    }

    public Player getNearPlayer() {
        return this.nearPlayer;
    }

    private SPlayer(Player player) {
        this.player = player;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) return;
        int PlayerID = gamer.getPlayerID();
        Language lang = gamer.getLanguage();

        int[] data = GameManager.getInstance().getSpectatorLoaders().getStats(PlayerID);
        this.speedSpec = data[0];
        this.alwaysFly = data[1];
        this.hideSpectators = data[2];
        this.vision = data[3];

        spectatorSettings = new SpectatorSettings(this, lang);

        sPlayers.put(player.getName(), this);
    }

    public Player getPlayer() {
        return player;
    }

    public int getSpeedSpec() {
        return speedSpec;
    }

    public void setSpeedSpec(int speedSpec) {
        this.speedSpec = speedSpec;
    }

    public int getVision() {
        return vision;
    }

    public void setVision(int vision) {
        if (vision == 1) {
            this.vision = 1;
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0));
        } else if (vision == 0) {
            this.vision = 0;
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    public void openInventory() {
        this.spectatorSettings.openInventory();
    }

    public SpectatorSettings getSpectatorSettings() {
        return this.spectatorSettings;
    }

    public void setAlwaysFly(int fly) {
        if (fly == 1) {
            this.alwaysFly = 1;
        } else if (fly == 0) {
            this.alwaysFly = 0;
        }
    }

    public int getAlwaysFly() {
        return this.alwaysFly;
    }

    public void setHideSpectators(int hide) {
        if (hide == 1) {
            this.hideSpectators = 1;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p == getPlayer())
                    continue;
                if (GAMER_MANAGER.getGamer(p).getGameMode() == GameModeType.SPECTATOR)
                    getPlayer().hidePlayer(p);
            }
        } else if (hide == 0) {
            this.hideSpectators = 0;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p == getPlayer())
                    continue;
                if (GAMER_MANAGER.getGamer(p).getGameMode() == GameModeType.SPECTATOR)
                    getPlayer().showPlayer(p);
            }
        }
    }

    public int getHideSpectators() {
        return this.hideSpectators;
    }
}
