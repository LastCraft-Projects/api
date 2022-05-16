package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerLvlUpEvent extends GamerEvent {

    private final int level;
    private final int expNextLevel;

    public GamerLvlUpEvent(BukkitGamer gamer, int level, int expNext) {
        super(gamer);
        this.level = level;
        this.expNextLevel = expNext;
    }
}
