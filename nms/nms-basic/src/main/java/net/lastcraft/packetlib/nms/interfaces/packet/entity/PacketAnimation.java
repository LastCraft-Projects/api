package net.lastcraft.packetlib.nms.interfaces.packet.entity;

import net.lastcraft.api.entity.npc.AnimationNpcType;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;

public interface PacketAnimation extends DPacketEntity<DEntity> {

    AnimationNpcType getAnimation();

    void setAnimation(AnimationNpcType animation);
}
