package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.entity.npc.NPC;
import net.lastcraft.api.player.BukkitGamer;

@Getter
public class GamerInteractNPCEvent extends GamerEvent {

    private final NPC npc;
    private final GamerInteractNPCEvent.Action action;

    public GamerInteractNPCEvent(BukkitGamer gamer, NPC npc, GamerInteractNPCEvent.Action action){
        super(gamer);
        this.npc = npc;
        this.action = action;
    }

    public enum Action {
        LEFT_CLICK, RIGHT_CLICK
    }

}
