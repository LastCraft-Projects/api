package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

@Deprecated
public class EntitySpawnListener extends DListener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if (GameSettings.minigame != MiniGameType.DEFAULT)
            e.setCancelled(true);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (GameSettings.minigame != MiniGameType.DEFAULT)
            e.setCancelled(true);
    }
}
