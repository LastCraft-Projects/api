package net.lastcraft.dartaapi.utils.core;

import lombok.experimental.UtilityClass;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.ChangeGameStateEvent;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.commands.LeaveCommand;
import net.lastcraft.dartaapi.commands.MoneyCommand;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.game.depend.LoginListener;
import net.lastcraft.dartaapi.game.module.WaitModule;
import net.lastcraft.dartaapi.gamemodes.GameModeScoreBoardTeam;
import net.lastcraft.dartaapi.listeners.*;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.bukkit.CheckMemory;
import net.lastcraft.dartaapi.utils.bukkit.WorldTime;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
@Deprecated
public class CoreUtil {

    public static boolean restart = false;
    private final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();
    private final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    @Deprecated
    public static void registerGame(String mapName, boolean alwaysDay){
        new MoneyCommand();
        new ItemsListener();
        new LoadHubs();
        new WaitModule();
        new LoginListener();
        new LeaveCommand();
        new ChunkListener();
        new InventoryListener();

        new WorldListener();

        WorldTime.addWorld("lobby", 6000);
        if (alwaysDay)
            WorldTime.addWorld(getGameWorld(), 6000);

        new SendInfo(mapName);
        new CheckMemory();
    }

    @Deprecated
    public static void registerGame(String mapName) {
        registerGame(mapName, true);
    }

    @Deprecated
    public static void registerLobby(int time){
        new MoneyCommand();
        new JoinListener(DartaAPI.getInstance());
        new LobbyGuardListener();
        new BlockPhysicsListener();
        new PlayerInteractListener();
        new DamageListener();
        new EntitySpawnListener();
        new ItemSpawnListener();
        new PhysicalListener();
        new WeatherListener();
        new ChunkListener();

        new WorldListener();

        WorldTime.addWorld("lobby", time);
    }

    public String getConfigDirectory() {
        return "/home/lastcraft/create/" + GAMER_MANAGER.getSpigot().getName().split("-")[0] + "/config";
    }

    public String getServerDirectory() {
        return "/home/lastcraft/servers/" + GAMER_MANAGER.getSpigot().getName();
    }

    @Deprecated
    public String getGameWorld() {
        String world = null;
        File folder = new File("/home/lastcraft/servers/" + GAMER_MANAGER.getSpigot().getName());
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                if (!file.getName().equals("logs")
                        && !file.getName().equals("lobby")
                        && !file.getName().equals("plugins")
                        && !file.getName().equals("timings")
                        && !file.getName().equals("endlobby")) {
                    world = file.getName();
                }
            }
        }
        return world;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Deprecated
    public static String getRandom(Collection<String> e) {
        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst()
                .orElse("limbo-1");
    }

    @Deprecated
    public static void restart() {
        BukkitUtil.runTask(() -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
                Language lang = Language.getDefault();
                if (gamer != null)
                    lang = gamer.getLanguage();
                player.kickPlayer(lang.getMessage("ERROR_TO_LOBBY"));
            });

            try {
                String world = getGameWorld();
                if (!Bukkit.unloadWorld(world, false) || restart) {
                    Bukkit.shutdown();
                    return;
                }
                FileUtils.cleanDirectory(new File(getServerDirectory() + "/lobby/playerdata"));
                GameModeScoreBoardTeam.resetBoard();

                SCORE_BOARD_API.removeDefaultTags();

                GamerAPI.clearGamers();

                GameManager.getInstance().getClass().newInstance();
                new WaitModule();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        GameState.setCurrent(GameState.WAITING);
        ChangeGameStateEvent changeGameStateEvent = new ChangeGameStateEvent(GameState.WAITING);
        BukkitUtil.callEvent(changeGameStateEvent);
    }
}
