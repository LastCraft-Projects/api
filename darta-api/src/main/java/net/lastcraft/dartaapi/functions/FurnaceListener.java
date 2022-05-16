package net.lastcraft.dartaapi.functions;

import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

public class FurnaceListener extends DListener {

    private static ArrayList<Inventory> furnaces = new ArrayList<>();

    public FurnaceListener() {
        furnaces.clear();
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof Furnace) {
            Furnace furnace = (Furnace) e.getInventory().getHolder();
            Block block = furnace.getBlock();
            if (block.getMetadata("furnace").isEmpty()) {
                if (block.getType() == Material.FURNACE) {
                    if (!furnaces.contains(e.getInventory()))
                    e.getInventory().setItem(1, new ItemStack(Material.COAL, 1 + (int)(Math.random() * 3.0D)));
                    furnaces.add(e.getInventory());
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block place = e.getBlock();
        if (place.getType().equals(Material.FURNACE))
            place.setMetadata("furnace", new FixedMetadataValue(DartaAPI.getInstance(), e.getPlayer().getName()));

    }



}
