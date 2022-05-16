package net.lastcraft.packetlib.nms.interfaces.entity;

import net.lastcraft.api.entity.npc.types.VillagerNPC;

public interface DEntityVillager extends DEntityLiving {

    VillagerNPC.Profession getProfession();

    void setVillagerProfession(VillagerNPC.Profession profession);
}
