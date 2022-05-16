package net.lastcraft.dartaapi.game.perk.type.vampirism;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VampListener {
    private static Map<String, Integer> playerVampirism = new ConcurrentHashMap<>();

    public VampListener(Player player, int count) {
        playerVampirism.put(player.getName(), count);
    }

    public static Map<String, Integer> getPlayerVampirism() {
        return playerVampirism;
    }
}