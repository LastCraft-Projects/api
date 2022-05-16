package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.SlimeNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntitySlime;
import org.bukkit.Location;

public class CraftSlimeNPC extends CraftLivingNPC implements SlimeNPC {

    public CraftSlimeNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        DEntitySlime slime =  NMS_MANAGER.createDEntity(DEntitySlime.class, location);
        slime.setSize(1);
        return slime;
    }

    @Override
    public int getSize() {
        return ((DEntitySlime)entity).getSize();
    }

    @Override
    public void setSize(int size) {
        ((DEntitySlime)entity).setSize(size);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.SLIME;
    }
}
