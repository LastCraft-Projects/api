package net.lastcraft.dartaapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class WorldTime {

    public void freezeTime(FreezeData freezeData) {
        TimeTask.TIME_FREEZER_MAP.put(freezeData.getWorldName().toLowerCase(), freezeData);
    }

    public void freezeTime(World world, long time, boolean isStorm) {
        freezeTime(new FreezeData(world.getName(), time, isStorm));
    }

    public void freezeTime(String worldName, long time, boolean isStorm) {
        freezeTime(new FreezeData(worldName, time, isStorm));
    }

    public boolean isFreezed(String worldName) {
        return TimeTask.TIME_FREEZER_MAP.containsKey(worldName.toLowerCase());
    }

    public boolean isFreezed(World world) {
        return world != null && isFreezed(world.getName());
    }

    public void resumeTime(String worldName) {
        TimeTask.TIME_FREEZER_MAP.remove(worldName.toLowerCase());
    }

    public void resumeTime(World world) {
        resumeTime(world.getName());
    }

    public class TimeTask implements Runnable {

        private static final Map<String, FreezeData> TIME_FREEZER_MAP = new HashMap<>();

        @Override
        public void run() {
            for (FreezeData freezeData : TIME_FREEZER_MAP.values()) {
                World world = Bukkit.getWorld(freezeData.getWorldName());

                if (world != null) {
                    world.setTime(freezeData.getTime());
                    world.setStorm(freezeData.isStorm());
                }
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public class FreezeData {

        private final String worldName;
        private final long time;
        private final boolean storm;

    }

}
