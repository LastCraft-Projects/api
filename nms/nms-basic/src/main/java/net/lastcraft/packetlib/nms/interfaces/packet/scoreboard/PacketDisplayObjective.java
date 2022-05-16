package net.lastcraft.packetlib.nms.interfaces.packet.scoreboard;

import net.lastcraft.api.scoreboard.DisplaySlot;
import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;
import net.lastcraft.packetlib.nms.scoreboard.DObjective;

public interface PacketDisplayObjective extends DPacket {

    void setObjective(DObjective objective);

    DObjective getObjective();

    void setDisplaySlot(DisplaySlot slot);

    DisplaySlot getSlot();
}
