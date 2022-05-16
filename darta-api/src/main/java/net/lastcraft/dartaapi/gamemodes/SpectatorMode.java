package net.lastcraft.dartaapi.gamemodes;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.dartaapi.boards.SpectatorBoard;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.game.spectator.SPlayer;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Deprecated
public class SpectatorMode {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public static void setSpectatorMode(Player player) {
        if (player == null || !player.isOnline())
            return;

        player.getInventory().setItem(0, ItemsListener.getTeleporter(player));
        player.getInventory().setItem(4, ItemsListener.getSpectatorSettings(player));
        player.getInventory().setItem(8, ItemsListener.getHub(player));

        try {
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
        } catch (Exception ignored) {

        }

        GameModeScoreBoardTeam.addPlayerSpectatorTeam(player);
        player.spigot().setCollidesWithEntities(false);

        player.setGameMode(GameMode.ADVENTURE);

        player.setAllowFlight(true);
        player.setFlying(true);

        SPlayer sPlayer = SPlayer.getSPlayer(player);

        switch (sPlayer.getSpeedSpec()) {
            case 0:
                PlayerUtil.removePotionEffect(player, PotionEffectType.SPEED);
                sPlayer.getSpectatorSettings().updateInventory();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                PlayerUtil.addPotionEffect(player, PotionEffectType.SPEED, (sPlayer.getSpeedSpec() - 1));
                sPlayer.getSpectatorSettings().updateInventory();
                break;
        }

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p == player) continue;
            BukkitGamer dp = GAMER_MANAGER.getGamer(p);
            if (dp == null)
                continue;
            if(dp.getGameMode() == GameModeType.SPECTATOR) {
                SPlayer splayer = SPlayer.getSPlayer(p);

                if(splayer == null)
                    continue;

                if(splayer.getHideSpectators() == 1) {
                    p.hidePlayer(player);
                }
                player.showPlayer(p);
                splayer.getSpectatorSettings().updateInventory();
            } else {
                p.hidePlayer(player);
            }
        }

        if (sPlayer.getAlwaysFly() == 1) {
            sPlayer.setAlwaysFly(1);
        } else if (sPlayer.getAlwaysFly() == 0) {
            sPlayer.setAlwaysFly(0);
        }
        if (sPlayer.getVision() == 1) {
            sPlayer.setVision(1);
        } else if (sPlayer.getVision() == 0) {
            sPlayer.setVision(0);
        }
        if (sPlayer.getHideSpectators() == 1) {
            sPlayer.setHideSpectators(1);
        } else if (sPlayer.getHideSpectators() == 0) {
            sPlayer.setHideSpectators(0);
        }
        sPlayer.getSpectatorSettings().updateInventory();

        SpectatorBoard.createBoard(player);
    }

    public static void removeSpectatorMode(Player player) {
        GameModeScoreBoardTeam.removePlayerSpectatorTeam(player);
        player.spigot().setCollidesWithEntities(true);

        player.setAllowFlight(false);
        player.setFlying(false);

        GameManager.getInstance().getSpectatorLoaders().addSetting(player);

        for(Player all : Bukkit.getOnlinePlayers()) {
            if (all == player) continue;
            if (GAMER_MANAGER.getGamer(all).getGameMode() == GameModeType.SPECTATOR) {
                SPlayer splayer = SPlayer.getSPlayer(all);
                if (splayer == null)
                    continue;
                if (splayer.getHideSpectators() == 1)
                    all.showPlayer(player);
                player.hidePlayer(all);
            } else {
                all.showPlayer(player);
            }
        }

        SPlayer.removeSPlayer(player);
    }

}
