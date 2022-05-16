package net.lastcraft.api.event.game;

import net.lastcraft.api.event.DEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public final class PlayerKillEvent extends DEvent {

    private Player player;
    private Entity killer;

    public static Map<String, PlayerKillEvent> players = new ConcurrentHashMap<>();
    private List<Player> kills = new ArrayList<>();

    public PlayerKillEvent(Player player, Entity killer) {
        this.player = player;
        this.killer = killer;
        if (killer instanceof Player) {
            if (!players.containsKey(killer.getName())) {
                players.put(killer.getName(), this);
                this.kills.add(player);
            } else {
                players.get(killer.getName()).kills.add(player);
            }
        }
    }

    public static PlayerKillEvent getPlayerKiller(Player player) {
        return players.get(player.getName());
    }

    public Player getPlayer() {
        return this.player;
    }

    public Entity getKiller() {
        return this.killer;
    }

    public List<Player> getKills() {
        return kills;
    }
}
