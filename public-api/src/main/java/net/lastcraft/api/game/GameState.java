package net.lastcraft.api.game;

@Deprecated
public enum GameState {
    WAITING,
    STARTING,
    GAME,
    END;

    private static GameState current = WAITING;
    private static long gameTime = 0;

    public static void setCurrent(GameState current){
        GameState.current = current;

        switch (current){
            case GAME:
                gameTime = System.currentTimeMillis();
                break;
            case END:
                gameTime = 0;
                break;
        }
    }

    public static long getGameTime(){
        return gameTime;
    }

    public static GameState getCurrent(){
        return current;
    }
}
