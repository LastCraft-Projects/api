package net.lastcraft.packetlib.nms.v1_12_R1.packet;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class DPacketBase<T extends Packet> implements DPacket {

    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private T packet;

    @Override
    public void sendPacket(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        if (packet == null) {
            packet = init();
        }
        if (packet == null) {
            return;
        }

        EntityPlayer handle = ((CraftPlayer) player).getHandle();
        if (handle == null) {
            return;
        }

        PlayerConnection playerConnection = handle.playerConnection;
        if (playerConnection == null) {
            return;
        }

        playerConnection.sendPacket(packet);
    }

    protected abstract T init();

    @Override
    public String toString() {
        return packet.getClass().getSimpleName();
    }
}
