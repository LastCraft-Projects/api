package net.lastcraft.api.event.gamer;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.player.BukkitGamer;
import org.bukkit.event.Cancellable;

@Getter
public class GamerChangeHiderStateEvent extends GamerEvent implements Cancellable {

    private final boolean hider;

    @Setter
    private boolean cancelled;

    public GamerChangeHiderStateEvent(BukkitGamer gamer, boolean hider) {
        super(gamer);
        this.hider = hider;
    }
}
