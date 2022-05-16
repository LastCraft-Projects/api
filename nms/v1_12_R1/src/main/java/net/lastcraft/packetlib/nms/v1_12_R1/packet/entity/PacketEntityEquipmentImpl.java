package net.lastcraft.packetlib.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.lastcraft.api.entity.EquipType;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketEntityEquipment;
import net.minecraft.server.v1_12_R1.EnumItemSlot;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityEquipment;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@Getter
public class PacketEntityEquipmentImpl extends DPacketEntityBase<PacketPlayOutEntityEquipment, DEntity>
        implements PacketEntityEquipment {

    private EquipType slot;
    private ItemStack itemStack;

    public PacketEntityEquipmentImpl(DEntity entity, EquipType slot, ItemStack itemStack) {
        super(entity);
        this.slot = slot;
        this.itemStack = itemStack;
    }

    @Override
    protected PacketPlayOutEntityEquipment init() {
        return new PacketPlayOutEntityEquipment(entity.getEntityID(), EnumItemSlot.valueOf(slot.name()),
                CraftItemStack.asNMSCopy(itemStack));
    }

    @Override
    public void setSlot(EquipType slot) {
        this.slot = slot;
        init();
    }


    @Override
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        init();
    }
}
