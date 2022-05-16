package net.lastcraft.packetlib.nms.v1_12_R1.gui;

import net.lastcraft.packetlib.nms.interfaces.gui.DAnvil;
import net.minecraft.server.v1_12_R1.*;

public class DAnvilImpl extends ContainerAnvil implements DAnvil {

    private EntityPlayer entityPlayer;

    public DAnvilImpl(EntityPlayer entityPlayer) {
        super(entityPlayer.inventory, entityPlayer.world,
                new BlockPosition(0, 0, 0), entityPlayer);
        this.entityPlayer = entityPlayer;
    }

    @Override
    public void openGui() {
        int containerId = entityPlayer.nextContainerCounter();
        PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(containerId, "minecraft:anvil",
                new ChatMessage("Repairing"));
        entityPlayer.playerConnection.sendPacket(packet);
        entityPlayer.activeContainer = this;
        entityPlayer.activeContainer.windowId = containerId;
        entityPlayer.activeContainer.addSlotListener(entityPlayer);
    }

    @Override
    public boolean canUse(EntityHuman entityhuman) {
        return true;
    }
}
