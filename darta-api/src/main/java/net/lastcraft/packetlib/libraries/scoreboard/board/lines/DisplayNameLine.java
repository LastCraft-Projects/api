package net.lastcraft.packetlib.libraries.scoreboard.board.lines;

import net.lastcraft.base.util.StringUtil;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreboardObjective;
import net.lastcraft.packetlib.nms.scoreboard.DObjective;
import net.lastcraft.packetlib.nms.scoreboard.ObjectiveActionMode;
import net.lastcraft.packetlib.nms.scoreboard.ObjectiveType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayNameLine {
    private static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();

    private DObjective dObjective;
    private final List<String> names = Collections.synchronizedList(new ArrayList<>());
    private String name;
    private long speed;
    private boolean animation;

    private Player owner;

    private int d = 0;
    private int t = 0;

    public DisplayNameLine() {
        dObjective = new DObjective("LastCraftBoard", "DisplayName", ObjectiveType.INTEGER);
        name = dObjective.getDisplayName();
        speed = -1;
        animation = false;
    }

    public void setName(String name) {
        this.name = name;
        this.dObjective.setDisplayName(name);
        this.names.clear();
        animation = false;
    }

    public void setNames(String name) {
        setName(name);
        animation = true;
        this.names.addAll(StringUtil.getAnimation(name));
    }

    public void setNames(List<String> names, String name) {
        setName(name);
        if (names.size() > 1) {
            animation = true;
            this.names.addAll(names);
        }
    }

    public DObjective getdObjective() {
        return dObjective;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public List<String> getNames() {
        return names;
    }

    public boolean isAnimation() {
        return animation;
    }

    public Long getSpeed() {
        return speed;
    }

    public Runnable updateObjective() {
        return () -> {
            try {
                if (d == this.names.size()) {
                    if (t == 120){
                        t = 0;
                        d = 0;
                    }
                    this.dObjective.setDisplayName(" §8§l» §6§l" + name + "  ");
                    this.t++;
                } else {
                    this.dObjective.setDisplayName(names.get(d));
                    ++this.d;
                }
                sendUpdatePacket();
            } catch (Exception ignored) {

            }
        };
    }

    private void sendUpdatePacket() {
        if (owner == null)
            return;

        PacketScoreboardObjective packet = PACKET_CONTAINER.getScoreboardObjectivePacket(this.dObjective,
                ObjectiveActionMode.UPDATE);
        packet.sendPacket(owner);
    }

    public void setPlayerName(Player owner) {
        this.owner = owner;
    }
}
