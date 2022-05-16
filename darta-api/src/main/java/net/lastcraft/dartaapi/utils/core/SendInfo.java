package net.lastcraft.dartaapi.utils.core;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.connector.Core;

import java.util.concurrent.TimeUnit;

@Deprecated
public class SendInfo extends Thread {

    private String mapName;
    private String channel;

    public SendInfo(String mapName) {
        this.mapName = mapName;
        this.channel = GameSettings.channel;
        start();
    }

    public SendInfo(String mapName, String channel){
        this.mapName = mapName;
        this.channel = channel;
        start();
    }

    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
                for (String lobby : GameSettings.hubs)
                    Core.sendMessageToCore(lobby, GameSettings.channel, mapName + ";" + PlayerUtil.getAlivePlayers().size() + ";" + GameSettings.slots + ";" + String.valueOf(GameState.getCurrent() == GameState.GAME || GameState.getCurrent() == GameState.END));

            } catch (Exception ignore) {
            }
        }
    }

}
