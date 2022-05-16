package net.lastcraft.packetlib.libraries.inventory.inventories;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.packetlib.libraries.inventory.GuiManagerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CraftDInventory implements DInventory {

    private final GuiManagerListener guiManagerListener;

    @Getter
    private final Inventory inventory;
    @Getter
    private final String name;
    private final int rows;
    @Getter
    private final TIntObjectMap<DItem> items = TCollections.synchronizedMap(new TIntObjectHashMap<>());

    @Getter
    @Setter
    private boolean disableAction;

    @Getter
    private InventoryAction inventoryAction = new InventoryAction() {
        @Override
        public void onOpen(Player player) {
            //nothing((
        }

        @Override
        public void onClose(Player player) {
            //nothing((
        }
    };


    public CraftDInventory(Player player, String name, int rows,
                           InventoryAction inventoryAction, GuiManagerListener guiManagerListener) {
        this.name = name;
        this.rows = rows;
        this.guiManagerListener = guiManagerListener;

        inventory = Bukkit.createInventory(player, 9 * rows, name);

        this.disableAction = true;

        if (inventoryAction != null) {
            this.inventoryAction = inventoryAction;
        }
    }

    @Override
    public void openInventory(Player player) {
        guiManagerListener.openInventory(player, this);
    }

    @Override
    public void openInventory(BukkitGamer gamer) {
        Player player = gamer.getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }

        openInventory(player);
    }

    @Override
    public void setItem(int slot, DItem item) {
        inventory.setItem(slot, item.getItem());
        items.put(slot, item);
    }

    @Override
    public void addItem(DItem dItem) {
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (inventory.getItem(slot) == null) {
                setItem(slot, dItem);
                return;
            }
        }
    }
    @Override
    public void removeItem(int slot) {
        DItem item = items.remove(slot);
        if (item == null) {
            return;
        }

        inventory.setItem(slot, null);
    }

    @Override
    public void clearInventory() {
        items.clear();
        inventory.clear();
    }

    @Override
    public int size() {
        return rows * 9;
    }

    @Override
    public void createInventoryAction(InventoryAction inventoryAction) {
        this.inventoryAction = inventoryAction;
    }
}
