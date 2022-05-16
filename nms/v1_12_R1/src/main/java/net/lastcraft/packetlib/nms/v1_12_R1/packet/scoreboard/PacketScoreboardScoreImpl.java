package net.lastcraft.packetlib.nms.v1_12_R1.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreboardScore;
import net.lastcraft.packetlib.nms.scoreboard.DScore;
import net.lastcraft.packetlib.nms.scoreboard.ScoreboardAction;
import net.lastcraft.packetlib.nms.util.ReflectionUtils;
import net.lastcraft.packetlib.nms.v1_12_R1.packet.DPacketBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardScore;

@AllArgsConstructor
@Getter
public class PacketScoreboardScoreImpl extends DPacketBase<PacketPlayOutScoreboardScore>
        implements PacketScoreboardScore {

    private DScore score;
    private ScoreboardAction action;

    @Override
    public void setScore(DScore score) {
        this.score = score;
        init();
    }


    @Override
    public void setAction(ScoreboardAction action) {
        this.action = action;
        init();
    }

    @Override
    protected PacketPlayOutScoreboardScore init() {
        PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore();

        ReflectionUtils.setFieldValue(packet, "a", score.getName());
        ReflectionUtils.setFieldValue(packet, "b", score.getDObjective().getName());
        ReflectionUtils.setFieldValue(packet, "c", score.getScore());
        ReflectionUtils.setFieldValue(packet, "d",
                PacketPlayOutScoreboardScore.EnumScoreboardAction.valueOf(action.name()));

        return packet;
    }
}
