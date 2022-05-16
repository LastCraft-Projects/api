package net.lastcraft.dartaapi.listeners;

import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPhysicsEvent;

@Deprecated
public class BlockPhysicsListener extends DListener {

    @EventHandler
    public void onPhysic(BlockPhysicsEvent e) {
        e.setCancelled(true);
    }

}
