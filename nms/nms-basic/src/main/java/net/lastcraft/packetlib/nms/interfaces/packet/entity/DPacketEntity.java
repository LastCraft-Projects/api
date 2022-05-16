package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;

public interface DPacketEntity<E extends DEntity> extends DPacket {

    E getEntity();

    void setEntity(E entity);
}
