package net.lastcraft.base.gamer.sections;

import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;
import lombok.Getter;
import lombok.Setter;
import net.lastcraft.base.gamer.friends.Friend;
import net.lastcraft.base.gamer.friends.FriendAction;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.sql.GlobalLoader;

public class FriendsSection extends Section {

    @Getter
    private final TIntSet friends = new TIntHashSet();

    @Getter
    @Setter
    private int friendsLimit = 20;

    public FriendsSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        this.friends.addAll(GlobalLoader.getFriends(baseGamer.getPlayerID()));
        this.friendsLimit = getFriendsLimit(baseGamer.getGroup());
        return true;
    }

    public boolean isFriend(int playerID) {
        return friends.contains(playerID);
    }

    public boolean isFriend(Friend friend) {
        return isFriend(friend.getPlayerId());
    }

    public void changeFriend(FriendAction friendAction, Friend friend) {
        switch (friendAction) {
            case ADD_FRIEND:
                friends.add(friend.getPlayerId());
                break;
            case REMOVE_FRIEND:
                friends.remove(friend.getPlayerId());
                break;
        }
    }

    public static int getFriendsLimit(Group group) {
        int friendsLimit;

        switch (group) {
            case DEFAULT:
                friendsLimit = 20;
                break;
            case GOLD:
                friendsLimit = 30;
                break;
            case DIAMOND:
                friendsLimit = 40;
                break;
            case EMERALD:
                friendsLimit = 50;
                break;
            case ADMIN:
                friendsLimit = 100;
                break;
            default:
                friendsLimit = 65;
        }

        return friendsLimit;
    }

    /*
    static {
        new TableConstructor("friends",
                new TableColumn("id", ColumnType.INT_11).primaryKey(true).unigue(true),
                new TableColumn("friend_id", ColumnType.INT_11).primaryKey(true).unigue(true)
        ).create(GlobalLoader.getMysqlDatabase());
    }
    */
}
