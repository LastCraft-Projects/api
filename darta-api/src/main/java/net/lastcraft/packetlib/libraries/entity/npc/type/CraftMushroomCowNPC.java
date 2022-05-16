package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.MushroomCowNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityMushroomCow;
import org.bukkit.Location;

public class CraftMushroomCowNPC extends CraftLivingNPC implements MushroomCowNPC {

    public CraftMushroomCowNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        return NMS_MANAGER.createDEntity(DEntityMushroomCow.class, location);
    }

    @Override
    public NpcType getType() {
        return NpcType.MUSHROOM_COW;
    }
}
