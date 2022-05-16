package net.lastcraft.packetlib.nms.interfaces.packet.world;

import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;

public interface PacketWorldParticles extends DPacket {

    void setData(int[] data);
}
