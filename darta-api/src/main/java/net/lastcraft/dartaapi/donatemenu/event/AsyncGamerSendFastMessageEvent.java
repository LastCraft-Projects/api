package net.lastcraft.dartaapi.donatemenu.event;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.dartaapi.donatemenu.FastMessage;
import org.bukkit.event.Cancellable;

import java.util.Set;

@Getter
@Setter
public class AsyncGamerSendFastMessageEvent extends GamerEvent implements Cancellable {

    private FastMessage fastMessage;

    private final Set<BukkitGamer> recipients;

    private boolean cancelled;

    public AsyncGamerSendFastMessageEvent(BukkitGamer gamer, FastMessage fastMessage, Set<BukkitGamer> gamers) {
        super(gamer, true);
        this.fastMessage = fastMessage;
        this.recipients = gamers;
    }
}
