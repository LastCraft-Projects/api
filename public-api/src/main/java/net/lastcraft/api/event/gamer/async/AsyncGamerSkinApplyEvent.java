package net.lastcraft.api.event.gamer.async;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;
import org.bukkit.event.Cancellable;

@Setter
@Getter
public class AsyncGamerSkinApplyEvent extends GamerEvent implements Cancellable {

    private boolean cancelled;

    public AsyncGamerSkinApplyEvent(BukkitGamer gamer) {
        super(gamer, true);
    }
}
