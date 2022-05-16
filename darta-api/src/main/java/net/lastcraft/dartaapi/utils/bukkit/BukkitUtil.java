package net.lastcraft.dartaapi.utils.bukkit;

import lombok.experimental.UtilityClass;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@UtilityClass
public class BukkitUtil {

    public void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public  void runTaskLater(long delay, Runnable runnable){
        Bukkit.getScheduler().runTaskLater(DartaAPI.getInstance(), runnable, delay);
    }

    public void runTaskLaterAsync(long delay, Runnable runnable){
        Bukkit.getScheduler().runTaskLaterAsynchronously(DartaAPI.getInstance(), runnable, delay);
    }

    public void runTask(Runnable runnable){
        Bukkit.getScheduler().runTask(DartaAPI.getInstance(), runnable);
    }

    public void runTaskAsync(Runnable runnable){
        Bukkit.getScheduler().runTaskAsynchronously(DartaAPI.getInstance(), runnable);
    }

}
