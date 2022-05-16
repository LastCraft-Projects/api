package net.lastcraft.dartaapi.listeners;

import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

@Deprecated
public class DropListener extends DListener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

}
