package net.lastcraft.packetlib.nms.v1_12_R1.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketEntityMetadata;
import net.lastcraft.packetlib.nms.v1_12_R1.entity.DEntityBase;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;

public class PacketEntityMetadataImpl extends DPacketEntityBase<PacketPlayOutEntityMetadata, DEntity>
        implements PacketEntityMetadata {

    public PacketEntityMetadataImpl(DEntity entity) {
        super(entity);
    }

    @Override
    protected PacketPlayOutEntityMetadata init() {
        Entity entity = ((DEntityBase)this.entity).getEntityNms();
        return new PacketPlayOutEntityMetadata(entity.getId(), entity.getDataWatcher(), true);
    }
}
