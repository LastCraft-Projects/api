package net.lastcraft.dartaapi.game.module;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.ChangeGameStateEvent;
import net.lastcraft.api.event.game.EndGameEvent;
import net.lastcraft.api.event.game.PlayerKillEvent;
import net.lastcraft.api.event.game.RestartGameEvent;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.game.depend.EndLobby;
import net.lastcraft.dartaapi.game.spectator.SpectatorMenu;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.listeners.*;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.CoreUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EndModule {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private List<DListener> endListeners = new ArrayList<>();

    public EndModule() {
        this.endGame();
    }

    private void endGame() {
        LastCraft.getScoreBoardAPI().unregisterObjectives();
        GameState.setCurrent(GameState.END);

        ChangeGameStateEvent changeGameStateEvent = new ChangeGameStateEvent(GameState.END);
        BukkitUtil.callEvent(changeGameStateEvent);

        endListeners.add(new BlockBreakListener());
        endListeners.add(new BlockPlaceListener());
        endListeners.add(new BlockPhysicsListener());
        endListeners.add(new DamageListener());
        endListeners.add(new DropListener());
        endListeners.add(new EntitySpawnListener());
        endListeners.add(new FoodListener());
        endListeners.add(new ItemSpawnListener());
        endListeners.add(new PhysicalListener());
        endListeners.add(new PickupListener());
        endListeners.add(new WeatherListener());
        StartModule.getGameListeners().forEach(DListener::unregisterListener);
        StartModule.getGameListeners().clear();
        StartModule.getPlayerInventory().stopPI();

        EndGameEvent endGameEvent = new EndGameEvent();
        BukkitUtil.callEvent(endGameEvent);

        WaitModule.getSelectionTeam().clear();
        SelectionTeam.getSelectedTeams().clear();

        BukkitUtil.runTask(() -> {
            for (Player player: Bukkit.getOnlinePlayers()) {
                BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
                if (gamer == null)
                    continue;
                gamer.setGameMode(GameModeType.ADVENTURE);

                player.getInventory().setItem(8, ItemsListener.getHub(player));
                player.closeInventory();
            }
        });

        endListeners.add(new EndLobby(endGameEvent));

        new Thread(() -> {
            try {
                Thread.sleep(10000);
                WaitModule.alertMessageAll(false, "RESTART_ARENA_MSG");
                Thread.sleep(5000);
                Bukkit.getOnlinePlayers().forEach(PlayerUtil::redirectToHub);
                Thread.sleep(5000);
                this.endListeners.forEach(DListener::unregisterListener);
                this.endListeners.clear();

                GameManager.getInstance().getStats().close();
                GameManager.getInstance().getSpectatorLoaders().close();

                Bukkit.unloadWorld("endlobby", false);

                WaitModule.perkgui.clear();
                PlayerKillEvent.players.clear();
                SpectatorMenu.clearData();

                RestartGameEvent restartGameEvent = new RestartGameEvent();
                BukkitUtil.callEvent(restartGameEvent);

                TeamManager.getTeams().clear();

                //BukkitUtil.runTask(AntiCheatUtils::unloadAndLoad);

                CoreUtil.restart();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

}
