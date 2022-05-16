package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.CreeperNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityCreeper;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import org.bukkit.Location;

public class CraftCreeperNPC extends CraftLivingNPC implements CreeperNPC {

    public CraftCreeperNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        return NMS_MANAGER.createDEntity(DEntityCreeper.class, location);
    }


    @Override
    public boolean isPowered() {
        return ((DEntityCreeper)entity).isPowered();
    }

    @Override
    public void setPowered(boolean flag) {
        if (this.isPowered() == flag)
            return;

        ((DEntityCreeper)entity).setPowered(flag);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.CREEPER;
    }
}
