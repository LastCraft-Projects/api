package net.lastcraft.dartaapi.utils;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.core.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Deprecated //todo мб удалить или переделать
public abstract class DListener implements Listener {

    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private final String name = getClass().getSimpleName();

    public static Set<DListener> listeners = Collections.synchronizedSet(new HashSet<>());

    protected DListener(){
        Bukkit.getPluginManager().registerEvents(this, DartaAPI.getInstance());
        MessageUtil.sendConsole("§a" + this.name);
        listeners.add(this);
    }

    public void unregisterListener() {
        HandlerList.unregisterAll(this);
        MessageUtil.sendConsole("§c" + this.name);
        listeners.remove(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
