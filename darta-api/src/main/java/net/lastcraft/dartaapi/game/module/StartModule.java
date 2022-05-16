package net.lastcraft.dartaapi.game.module;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.ChangeGameStateEvent;
import net.lastcraft.api.event.game.StartGameEvent;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.SoundType;
import net.lastcraft.dartaapi.functions.CheckAfk;
import net.lastcraft.dartaapi.functions.compass.CompassManager;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.game.depend.DeathListener;
import net.lastcraft.dartaapi.game.depend.GameListener;
import net.lastcraft.dartaapi.game.perk.PerksGui;
import net.lastcraft.dartaapi.game.perk.VanillaPerkListener;
import net.lastcraft.dartaapi.game.spectator.SpectatorListener;
import net.lastcraft.dartaapi.game.spectator.SpectatorMenu;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.guis.playerinvetory.PlayerInventory;
import net.lastcraft.dartaapi.guis.playerinvetory.PlayerInventoryListener;
import net.lastcraft.dartaapi.listeners.*;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StartModule {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final SoundAPI SOUND_API = LastCraft.getSoundAPI();

    private static List<DListener> gameListeners = new ArrayList<>();
    private static PlayerInventory playerInventory;

    public static List<DListener> getGameListeners() {
        return gameListeners;
    }

    static PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    StartModule(WaitModule waitModule) {
        this.startGame();
        waitModule.listeners.forEach(DListener::unregisterListener);
        waitModule.listeners.clear();
    }

    private void startGame() {
        GameState.setCurrent(GameState.GAME);
        ChangeGameStateEvent changeGameStateEvent = new ChangeGameStateEvent(GameState.GAME);
        BukkitUtil.callEvent(changeGameStateEvent);

        playerInventory = new PlayerInventory();
        playerInventory.startPI();

        gameListeners.add(new PlayerInventoryListener());
        gameListeners.add(new SpectatorListener());
        gameListeners.add(new CheckAfk());
        gameListeners.add(new GameListener());
        gameListeners.add(new VanillaPerkListener());
        SpectatorMenu.createMenu();

        if (!GameSettings.blockBreak) {
            gameListeners.add(new BlockBreakListener());
        }
        if (!GameSettings.blockPlace) {
            gameListeners.add(new BlockPlaceListener());
        }
        if (!GameSettings.blockPhysics) {
            gameListeners.add(new BlockPhysicsListener());
        }
        if (GameSettings.damage) {
            gameListeners.add(new DamageListener());
        }
        if (GameSettings.death) {
            gameListeners.add(new DeathListener());
        }
        if (!GameSettings.drop) {
            gameListeners.add(new DropListener());
        }
        if (!GameSettings.entitySpawn) {
            gameListeners.add(new EntitySpawnListener());
        }
        if (!GameSettings.fallDamage) {
            gameListeners.add(new FallListener());
        }
        if (!GameSettings.food) {
            gameListeners.add(new FoodListener());
        }
        if (!GameSettings.itemSpawn) {
            gameListeners.add(new ItemSpawnListener());
        }
        if (!GameSettings.physical) {
            gameListeners.add(new PhysicalListener());
        }
        if (!GameSettings.pickup) {
            gameListeners.add(new PickupListener());
        }
        if (!GameSettings.weather) {
            gameListeners.add(new WeatherListener());
        }
        if (GameSettings.TNTPrimed) {
            gameListeners.add(new TNTListener());
        }

        //Всех игроков распределить по тимам
        setTeamsPlayers();

        for (Player player : PlayerUtil.getAlivePlayers()) {
            GameManager.getInstance().getStats().createPlayerStats(player);
            GameManager.getInstance().getStats().addPlayerStats(player, "Games", 1);
        }

        StartGameEvent startGameEvent = new StartGameEvent(GameSettings.minigame);
        BukkitUtil.callEvent(startGameEvent);

        if (GameSettings.teamMode) {
            Bukkit.broadcastMessage("");
            WaitModule.alertMessageAll(false, "TEAM_NO_TEAM");
            Bukkit.broadcastMessage(" ");
        }

        //активация перков
        for (PerksGui perksGui : WaitModule.perkgui.values()) {
            perksGui.apply();
            //PerkSaveEvent achievement = newlocale PerkSaveEvent(perksGui.getOwner(), perksGui.getPerk());
            //BukkitUtil.callEvent(achievement);
        }

        for (Player player: PlayerUtil.getAlivePlayers()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer == null) continue;
            gamer.setGameMode(GameSettings.gamemode);
            SOUND_API.play(player, SoundType.LEVEL_UP);
            player.setAllowFlight(false);
            player.setFlying(false);
            CompassManager.createCompass(player);
        }
    }

    private void setTeamsPlayers() {
        for (Player player : PlayerUtil.getAlivePlayers()){
            TeamManager teamManager = SelectionTeam.getSelectedTeams().get(player);
            if (teamManager != null)
                continue;
            for (TeamManager team : TeamManager.getTeams().values()) {
                if (SelectionTeam.getPlayersInTeam(team) < GameSettings.playersInTeam) {
                    SelectionTeam.getSelectedTeams().put(player, team);
                    break;
                }
            }
        }
    }
}
