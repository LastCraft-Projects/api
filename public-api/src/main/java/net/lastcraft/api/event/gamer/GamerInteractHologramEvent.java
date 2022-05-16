package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerInteractHologramEvent extends GamerEvent {

    private final Hologram hologram;
    private final GamerInteractCustomStandEvent.Action action;

    public GamerInteractHologramEvent(BukkitGamer gamer, Hologram hologram,
                                      GamerInteractCustomStandEvent.Action action) {
        super(gamer);
        this.hologram = hologram;
        this.action = action;
    }
}
