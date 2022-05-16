package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.CowNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityCow;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import org.bukkit.Location;

public class CraftCowNPC extends CraftLivingNPC implements CowNPC {

    public CraftCowNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        return NMS_MANAGER.createDEntity(DEntityCow.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.COW;
    }
}
