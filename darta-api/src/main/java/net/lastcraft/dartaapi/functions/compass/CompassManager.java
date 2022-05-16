package net.lastcraft.dartaapi.functions.compass;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class CompassManager {

    private static Map<String, Compass> compasses = new ConcurrentHashMap<>();

    public static void createCompass(Player player){
        compasses.put(player.getName(), new Compass(player));
    }

    public static void removeCompass(Player player){
        compasses.remove(player.getName());
    }

    public static Compass getCompass(Player player){
        return compasses.get(player.getName());
    }

    public static Set<Compass> getCompasses(){
        return new HashSet<>(compasses.values());
    }

}
