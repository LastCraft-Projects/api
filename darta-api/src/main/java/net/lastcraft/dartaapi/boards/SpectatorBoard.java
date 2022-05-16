package net.lastcraft.dartaapi.boards;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.Board;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.dartaapi.utils.core.CoreUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;

@Deprecated
public class SpectatorBoard {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();

    public static void createBoard(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;
        Language lang = gamer.getLanguage();

        Board board = SCORE_BOARD_API.createBoard();
        board.setDynamicDisplayName(GameSettings.displayName);

        board.setLine(7, "§7" + GameSettings.typeGame.getType() + " " + StringUtil.getDate());
        board.setLine(6, StringUtil.getLineCode(6));
        board.updater(() -> {
            board.setDynamicLine(5, lang.getMessage("BOARD_PLAYERS")
                    + ": §e", String.valueOf(PlayerUtil.getAlivePlayers().size()));
            board.setDynamicLine(4, lang.getMessage("BOARD_SPECTATOR")
                    + ": §e", String.valueOf(PlayerUtil.getSpectators().size()));
        });
        board.setLine(3, StringUtil.getLineCode(3));
        board.setLine(2, lang.getMessage("BOARD_MAPS") + ": §c" + CoreUtil.getGameWorld());
        board.setLine(1, lang.getMessage("BOARD_SERVER") + ": §c" + GAMER_MANAGER.getSpigot().getName());

        board.showTo(player);
    }
}
