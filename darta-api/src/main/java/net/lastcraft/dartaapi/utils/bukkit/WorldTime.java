package net.lastcraft.dartaapi.utils.bukkit;

import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class WorldTime extends BukkitRunnable {

    public WorldTime(){
        this.runTaskTimer(DartaAPI.getInstance(), 0, 200);
    }

    private static final Map<String, Integer> WORLD = new ConcurrentHashMap<>();

    public static void addWorld(String worldName, int worldTime){
        WORLD.put(worldName.toLowerCase(), worldTime);
    }

    public static void removeWorld(String worldName){
        WORLD.remove(worldName.toLowerCase());
    }

    @Override
    public void run() {
        for (Map.Entry<String, Integer> pair : WORLD.entrySet()){
            World world = Bukkit.getWorld(pair.getKey());
            if (world == null)
                continue;

            world.setTime(pair.getValue());
        }
    }
}
