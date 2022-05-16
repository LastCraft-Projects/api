package net.lastcraft.dartaapi.guis.playerinvetory;

import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

@Deprecated
public class PlayerInventoryListener extends DListener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isAlive(player))
            PlayerInventory.removePlayerInventory(player);

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        for (Inventory inventory : PlayerInventory.getInventorys()) {
            if (e.getInventory().getName().equals(inventory.getName())) {
                e.setCancelled(true);
                break;
            }
        }
    }

}
