package net.lastcraft.dartaapi.gamemodes;

import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Deprecated
public class GhostMode {

    public static void setGhostMode(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));

        GameModeScoreBoardTeam.addPlayerGhostTeam(player);
        player.spigot().setCollidesWithEntities(false);

        player.setGameMode(GameMode.ADVENTURE);

        for (Player bPlayer : Bukkit.getOnlinePlayers()) {
            if (PlayerUtil.isGhost(bPlayer)) {
                bPlayer.showPlayer(player);
            } else {
                bPlayer.hidePlayer(player);
            }
        }
    }

    public static void removeGhostMode(Player player) {
        GameModeScoreBoardTeam.removePlayerGhostTeam(player);
        player.spigot().setCollidesWithEntities(true);

        for (Player bPlayer : Bukkit.getOnlinePlayers()) {
            if (PlayerUtil.isGhost(bPlayer)) {
                bPlayer.hidePlayer(player);
            } else {
                bPlayer.showPlayer(player);
            }
        }

    }

}
