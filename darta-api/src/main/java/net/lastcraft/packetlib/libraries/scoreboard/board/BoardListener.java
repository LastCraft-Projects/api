package net.lastcraft.packetlib.libraries.scoreboard.board;

import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.api.event.game.ChangeGameStateEvent;
import net.lastcraft.api.event.game.EndGameEvent;
import net.lastcraft.api.event.game.RestartGameEvent;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.scoreboard.Board;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.packetlib.libraries.scoreboard.ScoreBoardAPIImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class BoardListener extends DListener<DartaAPI> {

    private final ScoreBoardAPIImpl scoreBoardAPI;
    private final BoardManager boardManager;

    public BoardListener(ScoreBoardAPIImpl scoreBoardAPI) {
        super(scoreBoardAPI.getDartaAPI());
        this.scoreBoardAPI = scoreBoardAPI;
        this.boardManager = scoreBoardAPI.getBoardManager();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRestart(RestartGameEvent e) {
        boardManager.getPlayersBoard().clear();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(AsyncGamerQuitEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }
        scoreBoardAPI.removeBoard(player);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChangeGameState(ChangeGameStateEvent e) {
        if (e.getGameState() == GameState.WAITING || e.getGameState() == GameState.STARTING) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(scoreBoardAPI::removeBoard);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRestartGame(EndGameEvent e) {
        boardManager.getPlayersBoard().values().forEach(Board::remove);
    }
}
