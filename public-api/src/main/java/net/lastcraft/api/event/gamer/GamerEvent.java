package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.event.DEvent;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public abstract class GamerEvent extends DEvent {

    private final BukkitGamer gamer;

    protected GamerEvent(BukkitGamer gamer) {
        this(gamer, false);
    }

    protected GamerEvent(BukkitGamer gamer, boolean async) {
        super(async);
        this.gamer = gamer;
    }
}
