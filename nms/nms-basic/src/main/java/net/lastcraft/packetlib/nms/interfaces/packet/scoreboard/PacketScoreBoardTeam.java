package net.lastcraft.packetlib.nms.interfaces.packet.scoreboard;

import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;
import net.lastcraft.packetlib.nms.scoreboard.DTeam;
import net.lastcraft.packetlib.nms.scoreboard.TeamAction;

public interface PacketScoreBoardTeam extends DPacket {

    void setTeam(DTeam team);
    DTeam getTeam();

    void setTeamAction(TeamAction action);
    TeamAction getTeamAction();
}
