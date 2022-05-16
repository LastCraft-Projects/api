package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityChangeBlockEvent;

@Deprecated
public class PhysicalListener extends DListener {

    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        if (GameSettings.waterFlows){
            if (e.getToBlock().getType() == Material.AIR && e.getBlock().getType() != Material.DRAGON_EGG) {
                return;
            }
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFade(BlockFadeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onForm(BlockFormEvent e) {
        e.setCancelled(true);
    }


}
