package net.lastcraft.dartaapi.gamemodes;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Deprecated
public class SurvivalMode {
    public static void setSurvivalMode(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
    }
}
