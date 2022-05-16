package net.lastcraft.packetlib.nms.scoreboard;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.scoreboard.Collides;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class DTeam implements Cloneable {
    private final String displayName;
    private final String name;

    private boolean disableFfire = true;
    private boolean setFriendInv = false;
    private String prefix = "";
    private String suffix = "";
    private TagVisibility visibility = TagVisibility.ALWAYS;
    private Collides collides = Collides.NEVER;

    private final List<String> players = Collections.synchronizedList(new ArrayList<>());

    public DTeam(String name) {
        if (name.length() > 16) {
            this.name = name.substring(0, 15);
        } else {
            this.name = name;
        }

        this.displayName = name;
    }

    public DTeam(String name, String prefix, String suffix) {
        this(name);
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public DTeam(String name, Collection<String> players) {
        this(name);
        this.players.addAll(players);
    }

    public void addPlayer(String name) {
        this.players.add(name);
    }

    public boolean removePlayer(String name) {
        return this.players.remove(name);
    }

    public void addPlayers(Collection<String> names) {
        this.players.addAll(names);
    }

    public int packOptionData() {
        int data = 0;
        if (disableFfire) {
            data |= 1;
        }

        if (setFriendInv) {
            data |= 2;
        }

        return data;
    }
}
