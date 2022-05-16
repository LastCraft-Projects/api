package net.lastcraft.packetlib.nms.interfaces.entity;

import net.lastcraft.api.entity.npc.types.EnderDragonNPC;

public interface DEntityDragon extends DEntityLiving {

    EnderDragonNPC.Phase getPhase();

    void setPhase(EnderDragonNPC.Phase phase);
}
