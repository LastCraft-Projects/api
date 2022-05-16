package net.lastcraft.packetlib.libraries.entity.npc.type;

import net.lastcraft.api.entity.npc.NpcType;
import net.lastcraft.api.entity.npc.types.WolfNPC;
import net.lastcraft.packetlib.libraries.entity.npc.NPCManager;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityLiving;
import net.lastcraft.packetlib.nms.interfaces.entity.DEntityWolf;
import org.bukkit.DyeColor;
import org.bukkit.Location;

public class CraftWolfNPC extends CraftLivingNPC implements WolfNPC {

    public CraftWolfNPC(NPCManager npcManager, Location location) {
        super(npcManager, location);
    }

    @Override
    public DEntityLiving createNMSEntity() {
        DEntityWolf wolf = NMS_MANAGER.createDEntity(DEntityWolf.class, location);

        wolf.setAngry(false);
        wolf.setSitting(false);
        wolf.setCollarColor(DyeColor.RED);

        return wolf;
    }

    @Override
    public DyeColor getCollarColor() {
        return ((DEntityWolf)entity).getCollarColor();
    }

    @Override
    public void setCollarColor(DyeColor color) {
        ((DEntityWolf)entity).setCollarColor(color);
        sendPacketMetaData();
    }

    @Override
    public boolean isAngry() {
        return ((DEntityWolf)entity).isAngry();
    }

    @Override
    public void setAngry(boolean angry) {
        ((DEntityWolf)entity).setAngry(angry);
        sendPacketMetaData();
    }

    @Override
    public boolean isSitting() {
        return ((DEntityWolf)entity).isSitting();
    }

    @Override
    public void setSitting(boolean sitting) {
        ((DEntityWolf)entity).setSitting(sitting);
        sendPacketMetaData();
    }

    @Override
    public NpcType getType() {
        return NpcType.WOLF;
    }
}
