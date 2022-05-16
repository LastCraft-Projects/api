package net.lastcraft.base.util;

import lombok.experimental.UtilityClass;
import net.lastcraft.base.gamer.IBaseGamer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Cooldown {
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();
    private static final Map<String, Map<String, Long>> PLAYERS = new ConcurrentHashMap<>();

    public void addCooldown(String playerName, long ticks) {
        addCooldown(playerName, "global", ticks);
    }

    public void addCooldown(IBaseGamer gamer, long ticks) {
        addCooldown(gamer.getName(), ticks);
    }

    public boolean hasCooldown(String playerName) {
        return hasCooldown(playerName, "global");
    }

    public boolean hasCooldown(IBaseGamer gamer) {
        return hasCooldown(gamer.getName(), "global");
    }

    public boolean hasCooldown(IBaseGamer gamer, String type) {
        return hasCooldown(gamer.getName(), type);
    }

    public boolean hasCooldown(String playerName, String type) {
        if (playerName == null || type == null) {
            return false;
        }

        String name = playerName.toLowerCase();
        return PLAYERS.containsKey(name) && PLAYERS.get(name).containsKey(type.toLowerCase());
    }

    public int getSecondCooldown(String playerName, String type) {
        if (!hasCooldown(playerName, type)) {
            return 0;
        }
        String name = playerName.toLowerCase();
        Map<String, Long> cooldownData = PLAYERS.get(name);
        if (cooldownData == null) {
            return 0;
        }
        Long startTime = cooldownData.get(type.toLowerCase());
        if (startTime == null) {
            return 0;
        }
        int time = (int) ((startTime - System.currentTimeMillis()) / 50 / 20);
        return (time == 0 ? 1 : time);
    }

    public int getSecondCooldown(IBaseGamer gamer, String type) {
        return getSecondCooldown(gamer.getName(), type);
    }

    public void addCooldown(String playerName, String type, long ticks) {
        String name = playerName.toLowerCase();
        long time = System.currentTimeMillis() + ticks * 50;

        Map<String, Long> cooldownData = PLAYERS.get(name);
        if (cooldownData == null) {
            cooldownData = new ConcurrentHashMap<>();
            cooldownData.put(type.toLowerCase(), time);
            PLAYERS.put(name, cooldownData);
            return;
        }

        if (cooldownData.containsKey(type.toLowerCase()))
            return;

        cooldownData.put(type.toLowerCase(), time);
    }

    public void addCooldown(IBaseGamer gamer, String type, long ticks) {
        addCooldown(gamer.getName(), type, ticks);
    }

    /**
     * если есть кулдаун, вернет true
     * если его нет, то просто добавит его
     * @return - есть сейчас или нет кулдауна
     */
    public boolean hasOrAddCooldown(IBaseGamer gamer, String type, long tick) {
        if (!hasCooldown(gamer, type)) {
            addCooldown(gamer, type, tick);
            return false;
        }

        return true;
    }

    static {
        EXECUTOR_SERVICE.scheduleAtFixedRate(() -> PLAYERS.forEach((name, data) -> {
            data.forEach((type, time) -> {
                if (time < System.currentTimeMillis()) {
                    data.remove(type);
                }
            });
            if (data.isEmpty()) {
                PLAYERS.remove(name);
            }
        }), 0, 50, TimeUnit.MILLISECONDS);
    }
}
