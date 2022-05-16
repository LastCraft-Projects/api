package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.Group;

@Getter
public class GamerChangeGroupEvent extends GamerEvent {

    private final Group group;

    public GamerChangeGroupEvent(BukkitGamer gamer, Group group) {
        super(gamer);
        this.group = group;
    }
}
