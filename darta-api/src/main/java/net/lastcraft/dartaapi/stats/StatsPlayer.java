package net.lastcraft.dartaapi.stats;

import lombok.Getter;
import net.lastcraft.dartaapi.game.GameManager;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Deprecated
@Getter
public class StatsPlayer {
    private Player player;
    private int playerID;
    private Map<String, Integer> stats;

    public StatsPlayer(Player player, int playerID) {
        this.player = player;
        this.playerID = playerID;
        this.stats = new HashMap<>();

        for (String column : GameManager.getInstance().getStats().getStats()){
            stats.put(column, 0);
        }
    }

    public void addStats(String column, int amount){
        stats.put(column, stats.get(column) + amount);
    }

    public int getStats(String column){
        return stats.get(column);
    }
}
