package net.lastcraft.packetlib.nms.interfaces.packet.scoreboard;

import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;
import net.lastcraft.packetlib.nms.scoreboard.DObjective;
import net.lastcraft.packetlib.nms.scoreboard.ObjectiveActionMode;

public interface PacketScoreboardObjective extends DPacket {

    void setObjective(DObjective objective);

    DObjective getObjective();

    void setMode(ObjectiveActionMode mode);

    ObjectiveActionMode getMode();
}
