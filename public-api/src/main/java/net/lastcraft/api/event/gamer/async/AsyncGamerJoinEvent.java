package net.lastcraft.api.event.gamer.async;

import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;

public class AsyncGamerJoinEvent extends GamerEvent {

    public AsyncGamerJoinEvent(BukkitGamer gamer) {
        super(gamer, true);
    }
}
