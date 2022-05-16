package net.lastcraft.packetlib.nms.v1_12_R1.packet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.DPacketEntity;
import net.lastcraft.packetlib.nms.v1_12_R1.packet.DPacketBase;
import net.minecraft.server.v1_12_R1.Packet;

@AllArgsConstructor
@Getter
public abstract class DPacketEntityBase<A extends Packet, T extends DEntity> extends DPacketBase<A>
        implements DPacketEntity<T> {

    protected T entity;

    @Override
    public void setEntity(T entity) {
        this.entity = entity;
        init();
    }
}