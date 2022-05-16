package net.lastcraft.api.event.gamer.async;

import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;

public class AsyncGamerQuitEvent extends GamerEvent {

    public AsyncGamerQuitEvent(BukkitGamer gamer) {
        super(gamer, true);
    }
}
