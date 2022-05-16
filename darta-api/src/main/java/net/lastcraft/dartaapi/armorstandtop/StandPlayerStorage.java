package net.lastcraft.dartaapi.armorstandtop;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.lastcraft.api.player.BukkitGamer;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public final class StandPlayerStorage {

    private final Map<String, StandPlayer> players = new ConcurrentHashMap<>();

    void addPlayer(StandPlayer standPlayer) {
        players.put(standPlayer.getGamer().getName().toLowerCase(), standPlayer);
    }

    void removePlayer(String name) {
        players.remove(name);
    }

    @Nullable
    public StandPlayer getPlayer(BukkitGamer gamer) {
        return players.get(gamer.getName().toLowerCase());
    }
}
