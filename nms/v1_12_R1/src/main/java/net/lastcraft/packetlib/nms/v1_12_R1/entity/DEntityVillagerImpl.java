package net.lastcraft.packetlib.nms.v1_12_R1.entity;

import net.lastcraft.api.entity.npc.types.VillagerNPC;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityVillager;
import net.minecraft.server.v1_12_R1.EntityVillager;
import net.minecraft.server.v1_12_R1.World;

public class DEntityVillagerImpl extends DEntityLivingBase<EntityVillager> implements DEntityVillager {

    public DEntityVillagerImpl(World world) {
        super(new EntityVillager(world));
    }

    @Override
    public VillagerNPC.Profession getProfession() {
        return VillagerNPC.Profession.values()[entity.getProfession() + 1];
    }

    @Override
    public void setVillagerProfession(VillagerNPC.Profession profession) {
        entity.setProfession(profession.ordinal() - 1);
    }
}
