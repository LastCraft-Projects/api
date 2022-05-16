package net.lastcraft.base.gamer.constans;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Version {
    EMPTY(-1),

    MINECRAFT_1_8(47),

    MINECRAFT_1_9(107),
    MINECRAFT_1_9_1(108),
    MINECRAFT_1_9_2(109),
    MINECRAFT_1_9_3_AND_1_9_4(110),

    MINECRAFT_1_10(210),

    MINECRAFT_1_11(315),
    MINECRAFT_1_11_1_AND_1_11_2(316),

    MINECRAFT_1_12(335),
    MINECRAFT_1_12_1(338),
    MINECRAFT_1_12_2(340),

    MINECRAFT_1_13(393),
    MINECRAFT_1_13_1(401),
    MINECRAFT_1_13_2(404),

    MINECRAFT_1_14(477),
    MINECRAFT_1_14_1(480),
    MINECRAFT_1_14_2(485),
    ;

    private final int version;

    private static final TIntObjectMap<Version> VERSIONS = new TIntObjectHashMap<>();

    public static Version getVersion(int protocol) {
        Version version = VERSIONS.get(protocol);
        if (version != null) {
            return version;
        }

        return EMPTY;
    }

    static {
        for (Version version : values()) {
            VERSIONS.put(version.version, version);
        }
    }
}
