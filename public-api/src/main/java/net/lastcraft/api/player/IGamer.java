package net.lastcraft.api.player;

import net.lastcraft.api.game.GameModeType;

@Deprecated //todo удалить потом все тут и уйти от этого
public interface IGamer {

    /*
    нужны для того, чтобы не слать во время игры 100
    запросов, а все сделать в конце игры
     */
    int getMoneyLocal();
    int getExpLocal();
    int getKeysLocal();
    void addExpLocal(int expLocal);
    void addKeysLocal(int keysLocal);
    void addMoneyLocal(int moneyLocal);
    void setExpLocal();
    void setKeysLocal();
    void setMoneyLocal();
///////

    GameModeType getGameMode();
    void setGameMode(GameModeType gameMode);
}
