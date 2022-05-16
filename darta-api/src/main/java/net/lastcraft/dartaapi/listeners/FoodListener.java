package net.lastcraft.dartaapi.listeners;

import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;

@Deprecated
public class FoodListener extends DListener {

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

}
