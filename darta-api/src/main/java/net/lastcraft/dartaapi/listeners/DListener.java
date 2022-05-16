package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class DListener<T extends JavaPlugin> implements Listener {

    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    protected final T javaPlugin;

    protected DListener(T javaPlugin) {
        this.javaPlugin = javaPlugin;
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    public void unregisterListener() {
        HandlerList.unregisterAll(this);
    }
}
