package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameState;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

@Deprecated
public class InventoryListener extends DListener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (PlayerUtil.isSpectator(player) || GameState.getCurrent() != GameState.GAME){
            if (e.getInventory().getType() == InventoryType.CRAFTING) {
                if (e.getClick() == ClickType.NUMBER_KEY){
                    e.setCancelled(true);
                }
            }
        }
    }

}
