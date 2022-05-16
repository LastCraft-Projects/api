package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;

public interface PacketEntityLook extends DPacketEntity<DEntity> {

    void setPitch(byte pitch);

    void setYaw(byte yaw);

}
