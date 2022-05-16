package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.EnderDragonNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityDragon;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import org.bukkit.Location;

public class CraftEnderDragonNPC extends CraftLivingNPC implements EnderDragonNPC {

    public CraftEnderDragonNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        return NMS_MANAGER.createDEntity(DEntityDragon.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.ENDER_DRAGON;
    }

    @Override
    public Phase getPhase() {
        return ((DEntityDragon)entity).getPhase();
    }

    @Override
    public void setPhase(Phase phase) {
        ((DEntityDragon)entity).setPhase(phase);
        sendPacketMetaData();
    }
}
