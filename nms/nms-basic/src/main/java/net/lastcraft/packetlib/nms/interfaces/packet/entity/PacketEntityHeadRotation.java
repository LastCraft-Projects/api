package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;

public interface PacketEntityHeadRotation extends DPacketEntity<DEntity> {

    void setYaw(byte yaw);
}
