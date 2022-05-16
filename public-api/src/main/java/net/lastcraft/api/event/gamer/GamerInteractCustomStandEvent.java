package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerInteractCustomStandEvent extends GamerEvent {

    private final CustomStand stand;
    private final Action action;

    public GamerInteractCustomStandEvent(BukkitGamer gamer, CustomStand stand, Action action) {
        super(gamer);
        this.stand = stand;
        this.action = action;
    }

    public enum Action {
        LEFT_CLICK, RIGHT_CLICK
    }
}
