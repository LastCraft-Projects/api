package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.BlazeNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityBlaze;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import org.bukkit.Location;

public class CraftBlazeNPC extends CraftLivingNPC implements BlazeNPC {

    public CraftBlazeNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        return NMS_MANAGER.createDEntity(DEntityBlaze.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.BLAZE;
    }
}
