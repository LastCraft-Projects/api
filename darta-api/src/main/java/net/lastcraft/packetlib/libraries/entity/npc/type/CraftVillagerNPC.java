package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.VillagerNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityVillager;
import org.bukkit.Location;

public class CraftVillagerNPC extends CraftLivingNPC implements VillagerNPC {

    public CraftVillagerNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        DEntityVillager villager = NMS_MANAGER.createDEntity(DEntityVillager.class, location);
        villager.setVillagerProfession(Profession.FARMER);
        return villager;
    }

    @Override
    public Profession getProfession() {
        return ((DEntityVillager)entity).getProfession();
    }

    @Override
    public void setVillagerProfession(Profession profession) {
        ((DEntityVillager)entity).setVillagerProfession(profession);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.VILLAGER;
    }
}
