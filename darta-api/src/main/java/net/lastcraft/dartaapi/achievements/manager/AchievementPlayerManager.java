package net.lastcraft.dartaapi.achievements.manager;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.dartaapi.achievements.achievement.AchievementPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AchievementPlayerManager {

    private final Map<String, AchievementPlayer> achievementPlayers = new ConcurrentHashMap<>();
    private final GamerManager gamerManager = LastCraft.getGamerManager();

    public AchievementPlayer getAchievementPlayer(int playerID) {
        BukkitGamer gamer = gamerManager.getGamer(playerID);
        if (gamer != null)
            return getAchievementPlayer(gamer.getName());

        return null;
    }

    public AchievementPlayer getAchievementPlayer(String name) {
        return achievementPlayers.get(name.toLowerCase());
    }

    public AchievementPlayer getAchievementPlayer(Player player) {
        return getAchievementPlayer(player.getName());
    }

    public AchievementPlayer getOrCreateAchievementPlayer(BukkitGamer gamer, AchievementManager achievementManager) {
        AchievementPlayer achievementPlayer = getAchievementPlayer(gamer.getName());
        if (achievementPlayer != null) {
            return achievementPlayer;
        }

        achievementPlayer = new AchievementPlayer(gamer, achievementManager);
        addAchievementPlayer(achievementPlayer);
        return achievementPlayer;
    }

    public boolean contains(String name) {
        return achievementPlayers.containsKey(name.toLowerCase());
    }

    public void addAchievementPlayer(AchievementPlayer achievementPlayer) {
        if (achievementPlayer == null)
            return;

        String name = achievementPlayer.getGamer().getName().toLowerCase();
        if (achievementPlayers.containsKey(name))
            return;

        achievementPlayers.put(name, achievementPlayer);
    }

    public Map<String, AchievementPlayer> getAchievementPlayers() {
        return new HashMap<>(achievementPlayers);
    }

    public void removeAchievementPlayer(String name) {
        achievementPlayers.remove(name.toLowerCase());
    }
}
