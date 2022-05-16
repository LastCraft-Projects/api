package net.lastcraft.packetlib.libraries.entity.customstand;

import lombok.AllArgsConstructor;
import net.lastcraft.api.entity.EquipType;
import net.lastcraft.packetlib.libraries.entity.depend.EntityEquipImpl;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityArmorStand;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketEntityEquipment;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public final class StandEquip extends EntityEquipImpl {

    private final CraftStand stand;

    @Override
    public void setItem(EquipType equipType, ItemStack itemStack) {
        if (itemStack == null)
            return;

        DEntityArmorStand armorStand = stand.getEntity();

        items.put(equipType, itemStack);
        armorStand.setEquipment(equipType, itemStack);

        PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(armorStand, equipType, itemStack);
        stand.sendPacket(packet);
    }

    @Override
    public boolean removeItem(EquipType equipType) {
        DEntityArmorStand armorStand = stand.getEntity();
        if (armorStand == null)
            return false;

        if (items.remove(equipType) != null) {
            armorStand.setEquipment(equipType, null);

            PacketEntityEquipment packet = PACKET_CONTAINER.getEntityEquipmentPacket(armorStand, equipType, null);
            stand.sendPacket(packet);
            return true;
        }

        return false;
    }
}
