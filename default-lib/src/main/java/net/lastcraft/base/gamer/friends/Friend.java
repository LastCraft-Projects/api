package net.lastcraft.base.gamer.friends;

import net.lastcraft.base.gamer.IBaseGamer;

public interface Friend {

    int getPlayerId();

    boolean isOnline();

    <T extends IBaseGamer> T getGamer();

}
