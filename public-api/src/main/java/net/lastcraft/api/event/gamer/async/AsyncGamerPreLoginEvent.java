package net.lastcraft.api.event.gamer.async;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@Getter
public class AsyncGamerPreLoginEvent extends GamerEvent implements Cancellable {

    private final AsyncPlayerPreLoginEvent event;

    @Setter
    private boolean cancelled;

    public AsyncGamerPreLoginEvent(BukkitGamer gamer, AsyncPlayerPreLoginEvent event) {
        super(gamer, true);
        this.event = event;
    }
}
