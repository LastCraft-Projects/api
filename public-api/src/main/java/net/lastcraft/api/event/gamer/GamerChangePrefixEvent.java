package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerChangePrefixEvent extends GamerEvent {

    private final String prefix;

    public GamerChangePrefixEvent(BukkitGamer gamer, String prefix) {
        super(gamer);
        this.prefix = prefix;
    }
}

