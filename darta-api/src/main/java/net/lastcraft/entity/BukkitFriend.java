package net.lastcraft.entity;

import lombok.RequiredArgsConstructor;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.friends.Friend;

@RequiredArgsConstructor
public final class BukkitFriend implements Friend {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public final int friendId;

    @Override
    public int getPlayerId() {
        return friendId;
    }

    @Override
    public boolean isOnline() {
        return GAMER_MANAGER.getGamer(friendId) != null;
    }

    @Override
    public <T extends IBaseGamer> T getGamer() {
        return (T) GAMER_MANAGER.getOrCreate(friendId);
    }
}
