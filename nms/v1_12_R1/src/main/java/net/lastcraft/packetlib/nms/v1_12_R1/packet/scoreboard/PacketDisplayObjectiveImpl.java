package net.lastcraft.packetlib.nms.v1_12_R1.packet.scoreboard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.scoreboard.DisplaySlot;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketDisplayObjective;
import net.lastcraft.packetlib.nms.scoreboard.DObjective;
import net.lastcraft.packetlib.nms.util.ReflectionUtils;
import net.lastcraft.packetlib.nms.v1_12_R1.packet.DPacketBase;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardDisplayObjective;

@AllArgsConstructor
@Getter
public class PacketDisplayObjectiveImpl extends DPacketBase<PacketPlayOutScoreboardDisplayObjective>
        implements PacketDisplayObjective {

    private DisplaySlot slot;
    private DObjective objective;

    @Override
    public void setObjective(DObjective objective) {
        this.objective = objective;
        init();
    }

    @Override
    public void setDisplaySlot(DisplaySlot slot) {
        this.slot = slot;
        init();
    }

    @Override
    protected PacketPlayOutScoreboardDisplayObjective init() {
        PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();

        ReflectionUtils.setFieldValue(packet, "a", slot.ordinal());
        ReflectionUtils.setFieldValue(packet, "b", (objective == null ? "" : objective.getName()));

        return packet;
    }
}
