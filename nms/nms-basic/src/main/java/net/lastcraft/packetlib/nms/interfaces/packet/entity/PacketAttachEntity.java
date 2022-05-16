package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;

public interface PacketAttachEntity extends DPacketEntity<DEntity> {

    void setVehicle(DEntity vehicle);

    DEntity getVehicle();
}
