package net.lastcraft.api.event.gamer;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.hologram.lines.ItemDropLine;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerPickupHoloEvent extends GamerEvent {

    private final Hologram hologram;
    private final ItemDropLine line;

    @Setter
    private boolean remove;

    public GamerPickupHoloEvent(BukkitGamer gamer, Hologram hologram, ItemDropLine itemDropLine){
        super(gamer);
        this.hologram = hologram;
        this.line = itemDropLine;
    }
}
