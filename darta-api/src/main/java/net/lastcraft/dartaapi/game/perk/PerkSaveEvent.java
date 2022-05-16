package net.lastcraft.dartaapi.game.perk;

import net.lastcraft.api.event.DEvent;
import org.bukkit.entity.Player;

public class PerkSaveEvent extends DEvent {
    private Player player;
    private Perk perk;

    public PerkSaveEvent(Player player, Perk perk) {
        this.player = player;
        this.perk = perk;
    }

    public Player getPlayer() {
        return player;
    }

    public Perk getPerk() {
        return perk;
    }
}
