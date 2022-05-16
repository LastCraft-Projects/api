package net.lastcraft.packetlib.nms.interfaces.packet.scoreboard;

import net.lastcraft.packetlib.nms.interfaces.packet.DPacket;
import net.lastcraft.packetlib.nms.scoreboard.DScore;
import net.lastcraft.packetlib.nms.scoreboard.ScoreboardAction;

public interface PacketScoreboardScore extends DPacket {

    DScore getScore();

    void setScore(DScore score);

    ScoreboardAction getAction();

    void setAction(ScoreboardAction action);
}
