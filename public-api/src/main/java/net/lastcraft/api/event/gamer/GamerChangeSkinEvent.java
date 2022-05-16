package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.skin.Skin;

@Getter
public class GamerChangeSkinEvent extends GamerEvent {

    private final Skin skin;

    public GamerChangeSkinEvent(BukkitGamer gamer, Skin skin) {
        super(gamer);
        this.skin = skin;
    }
}
