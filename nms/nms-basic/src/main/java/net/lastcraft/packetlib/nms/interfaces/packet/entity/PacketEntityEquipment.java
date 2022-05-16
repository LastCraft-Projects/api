package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.api.entity.EquipType;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import org.bukkit.inventory.ItemStack;

public interface PacketEntityEquipment extends DPacketEntity<DEntity> {

    void setSlot(EquipType slot);

    EquipType getSlot();

    void setItemStack(ItemStack itemStack);

    ItemStack getItemStack();
}
