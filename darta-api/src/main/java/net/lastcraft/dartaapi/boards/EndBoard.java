package net.lastcraft.dartaapi.boards;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.scoreboard.Board;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.util.StringUtil;

@Deprecated
public class EndBoard  {

    private static final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();

    public static void createBoard(BukkitGamer gamer, String line1, String line2) {
        if (gamer == null) {
            return;
        }

        Board board = SCORE_BOARD_API.createBoard();
        board.setDynamicDisplayName(GameSettings.displayName);

        board.setLine(7, "§7" + GameSettings.typeGame.getType() + " " + StringUtil.getDate());
        board.setLine(6, StringUtil.getLineCode(6));
        board.setLine(5, StringUtil.getLineCode(5));
        board.setLine(4, line1);
        board.setLine(3, line2);
        board.setLine(2, StringUtil.getLineCode(2));
        board.setLine(1, StringUtil.getLineCode(1));

        board.showTo(gamer);
    }
}
