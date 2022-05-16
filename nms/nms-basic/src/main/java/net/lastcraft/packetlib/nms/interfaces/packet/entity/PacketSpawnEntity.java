package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.types.EntitySpawnType;

public interface PacketSpawnEntity extends DPacketEntity<DEntity> {

    EntitySpawnType getEntitySpawnType();

    void setEntitySpawnType(EntitySpawnType entitySpawnType);

    int getObjectData();

    void setObjectData(int objectData);
}
