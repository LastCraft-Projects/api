package net.lastcraft.packetlib.nms.v1_12_R1.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketMount;
import net.lastcraft.packetlib.nms.v1_12_R1.entity.DEntityBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutMount;

public class PacketMountImpl extends DPacketEntityBase<PacketPlayOutMount, DEntity> implements PacketMount {

    public PacketMountImpl(DEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutMount init() {
        return new PacketPlayOutMount(((DEntityBase)entity).getEntityNms());
    }
}
