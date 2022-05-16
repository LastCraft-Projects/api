package net.lastcraft.api.entity;

import net.lastcraft.api.depend.PacketEntity;

public interface PacketEntityLiving extends PacketEntity {

    void setLook(float yaw, float pitch);

    /**
     * содержит все вещи, что сейчас одеты на энтити
     * @return - получить инвентарь жнтити
     */
    EntityEquip getEntityEquip();



}
