package net.lastcraft.api.event.game;

import net.lastcraft.api.event.DEvent;
import net.lastcraft.api.game.MiniGameType;

@Deprecated
public class StartGameEvent extends DEvent {

    private MiniGameType miniGameType;

    public StartGameEvent(MiniGameType miniGameType) {
        this.miniGameType = miniGameType;
    }

    public MiniGameType getMiniGameType() {
        return miniGameType;
    }
}
