package net.lastcraft.packetlib.nms.interfaces.gui;

import net.lastcraft.packetlib.nms.types.EnchantingSlot;
import org.bukkit.inventory.ItemStack;

public interface DEnchantingTable extends DNmsGui {

    void addItem(EnchantingSlot slot, ItemStack stack);

    void setTitle(String title);
}
