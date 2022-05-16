package net.lastcraft.api.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Deprecated
public enum TypeGame {
    SOLO("Solo"),
    DOUBLES("Doubles"),
    TEAM("Team"),
    RUSH("Rush")

    ;

    private final String type;

    @Override
    public String toString() {
        return type;
    }
}
