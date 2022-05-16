package net.lastcraft.api.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashSet;

@Deprecated
public class GameSettings {
    public static String displayName = "DartaAPI";
    public static String prefix = "§6DartaAPI §8| §f";

    public static Location lobbyLoc = new Location(Bukkit.getWorlds().get(0), 0.0, 60.0, 0.0);
    public static Location spectatorLoc = new Location(Bukkit.getWorlds().get(0), 0.0, 60.0, 0.0);

    public static GameModeType gamemode = GameModeType.SURVIVAL;
    public static MiniGameType minigame = MiniGameType.DEFAULT;
    public static TypeGame typeGame = TypeGame.SOLO;

    public static boolean teamMode = false;
    public static int numberOfTeams = 4;
    public static int playersInTeam = 4;

    public static int slots = 16;
    public static int toStart = slots - (slots / 3);

    public static String hubPrefix = "hub";
    public static Collection<String> hubs = new HashSet<>();
    public static String channel = "dapi";

    public static boolean blockBreak = true;
    public static boolean blockPlace = true;
    public static boolean blockPhysics = false;
    public static boolean damage = false;
    public static boolean death = true;
    public static boolean drop = true;
    public static boolean entitySpawn = true;
    public static boolean fallDamage = true;
    public static boolean food = true;
    public static boolean itemSpawn = true;
    public static boolean physical = false;
    public static boolean pickup = true;
    public static boolean weather = false;

    public static boolean canDropOnDeath = false;
    public static boolean TNTPrimed = false;
    public static boolean waterFlows = false;
}
