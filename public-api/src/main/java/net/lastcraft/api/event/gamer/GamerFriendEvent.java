package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.friends.Friend;
import net.lastcraft.base.gamer.friends.FriendAction;

@Getter
public class GamerFriendEvent extends GamerEvent {

    private final Friend friend;
    private final FriendAction action;

    public GamerFriendEvent(BukkitGamer gamer, Friend friend, FriendAction action) {
        super(gamer);
        this.friend = friend;
        this.action = action;
    }
}
