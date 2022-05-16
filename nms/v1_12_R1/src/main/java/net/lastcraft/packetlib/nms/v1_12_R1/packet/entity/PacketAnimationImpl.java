package net.lastcraft.packetlib.nms.v1_12_R1.packet.entity;

import lombok.Getter;
import net.lastcraft.api.entity.npc.AnimationNpcType;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntity;
import net.lastcraft.packetlib.nms.interfaces.packet.entity.PacketAnimation;
import net.lastcraft.packetlib.nms.v1_12_R1.entity.DEntityBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutAnimation;

@Getter
public class PacketAnimationImpl extends DPacketEntityBase<PacketPlayOutAnimation, DEntity> implements PacketAnimation {

    private AnimationNpcType animation;

    public PacketAnimationImpl(DEntity entity, AnimationNpcType animation) {
        super(entity);
        this.animation = animation;
    }

    @Override
    public void setAnimation(AnimationNpcType animation) {
        this.animation = animation;
        init();
    }

    @Override
    protected PacketPlayOutAnimation init() {
        return new PacketPlayOutAnimation(((DEntityBase)entity).getEntityNms(), animation.ordinal());
    }
}
