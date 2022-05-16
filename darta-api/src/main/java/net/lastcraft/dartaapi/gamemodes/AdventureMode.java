package net.lastcraft.dartaapi.gamemodes;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

@Deprecated
public class AdventureMode {
    public static void setAdventureMode(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
    }
}
