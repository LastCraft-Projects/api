package net.lastcraft.api.event.game;

import net.lastcraft.api.event.DEvent;
import net.lastcraft.api.game.GameState;

@Deprecated
public class ChangeGameStateEvent extends DEvent {

    private GameState gameState;

    public ChangeGameStateEvent(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getGameState() {
        return this.gameState;
    }
}
