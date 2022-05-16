package net.lastcraft.packetlib.libraries.entity.npc;

import lombok.AllArgsConstructor;
import net.lastcraft.api.entity.EquipType;
import net.lastcraft.packetlib.libraries.entity.depend.EntityEquipImpl;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketEntityEquipment;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public final class NpcEquip extends EntityEquipImpl {

    private final CraftNPC npc;

    @Override
    public void setItem(EquipType equipType, ItemStack itemStack) {
        DEntityLiving entity = npc.getEntity();
        if (itemStack == null || entity == null)
            return;

        items.put(equipType, itemStack);
        entity.setEquipment(equipType, itemStack);

        PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(entity, equipType, itemStack);
        npc.sendNearby(packet);
    }

    @Override
    public boolean removeItem(EquipType equipType) {
        DEntityLiving entity = npc.getEntity();
        if (entity == null)
            return false;

        if (items.remove(equipType) != null) {
            entity.setEquipment(equipType, null);

            PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(entity, equipType, null);
            npc.sendNearby(packet);
            return true;
        }

        return false;
    }
}
