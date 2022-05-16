package net.lastcraft.dartaapi.boards;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.Board;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.dartaapi.game.module.WaitModule;
import net.lastcraft.dartaapi.utils.core.CoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Deprecated
public class WaitingBoard {

    private int s;

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();

    public WaitingBoard(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;
        Language lang = gamer.getLanguage();

        Board board = SCORE_BOARD_API.createBoard();
        board.setDynamicDisplayName(GameSettings.displayName);

        board.setLine(9, "§7" + GameSettings.typeGame.getType() + " " + StringUtil.getDate());
        board.setLine(8, StringUtil.getLineCode(8));
        board.updater(() -> {
            board.setDynamicLine(7, lang.getMessage("BOARD_PLAYERS") + ": §e", Bukkit.getOnlinePlayers().size() + "/" + GameSettings.slots);
            if (GameSettings.toStart > Bukkit.getOnlinePlayers().size() && GameState.getCurrent() == GameState.WAITING){
                board.setDynamicLine(6, lang.getMessage( "BOARD_FOR_START") + ": §e", String.valueOf(GameSettings.toStart - Bukkit.getOnlinePlayers().size()));
            } else {
                board.setLine(6, StringUtil.getLineCode(6));
            }
            if (GameState.getCurrent() == GameState.WAITING) {
                ++this.s;
                StringBuilder t = new StringBuilder();
                for (int i = 1; i < s; ++i) {
                    t.append(".");
                }
                board.setDynamicLine(3, "§r" + lang.getMessage("BOARD_WAIT"), t.toString());
                if (this.s == 4) {
                    s = 0;
                }
            } else {
                board.setDynamicLine(3, lang.getMessage("BOARD_TO_START") + ": §e", StringUtil.getCompleteTime(WaitModule.getTime()));
            }
        });
        board.setLine(5, lang.getMessage("BOARD_MAPS") + ": §a" + CoreUtil.getGameWorld());
        board.setLine(4, StringUtil.getLineCode(4));
        board.setLine(2, StringUtil.getLineCode(2));
        board.setLine(1, lang.getMessage("BOARD_SERVER") + ": §c" + GAMER_MANAGER.getSpigot().getName());

        board.showTo(player);
    }

}
