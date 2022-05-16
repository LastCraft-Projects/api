package net.lastcraft.api.game;

@Deprecated
public enum MiniGameType {
    SW_SOLO("SkyWars"),
    SW_TEAM("SkyWars"),
    LW_SOLO("LuckyWars"),
    LUCKYWARS("LuckyWars"),
    SG("SurvivalGames"),
    BW("BedWars"),
    EW("EggWars"),
    KW_SOLO("KitWars"),
    KW_TEAM("KitWars"),
    SURVIVAL("Survival"),
    DEFAULT("Other");

    private String name;

    MiniGameType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
