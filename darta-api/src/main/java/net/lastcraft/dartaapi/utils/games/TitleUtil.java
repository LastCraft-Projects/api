package net.lastcraft.dartaapi.utils.games;

import lombok.experimental.UtilityClass;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.TitleAPI;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

@UtilityClass
@Deprecated
public class TitleUtil {

    private static final TitleAPI TITLE_API = LastCraft.getTitlesAPI();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public void StartGameTitle(Player player, String subtitle, int time) {
        String title;
        String subTitle;

        Language lang = GAMER_MANAGER.getGamer(player).getLanguage();
        if (time == 10 ) {
            title = "§6§l" + GameSettings.minigame.toString();
            subTitle = lang.getMessage("WELCOME_TITLE");
            TITLE_API.sendTitle(player, title, subTitle, 0, 10 * 20, 0);
            return;
        }
        if (time == 0) {
            List<String> list = StringUtil.getAnimationTitle(lang.getMessage("START_TITLE_MSG"),
                    "§c", "§e", 6);
            BukkitUtil.runTaskAsync(() -> SendAnimationTitle(player, subtitle));
            list.clear();
            return;
        }

        title = "§6§l" + GameSettings.minigame.toString();
        subTitle = lang.getMessage("TO_START_TITLE") + " " +
                (time >= 3 ? "§a" : "") +
                (time == 2 ? "§e" : "") +
                (time == 1 ? "§c" : "") + StringUtil.getUTFNumber(time);
        TITLE_API.sendTitle(player, title, subTitle, 0, 22 * 20, 22 * 20);

    }

    public void StartGameTitle(Player player, String subtitle, String type) {
        String game = GameSettings.minigame.toString();
        TITLE_API.sendTitle(player, "§6§l" + game, type, 0, 10 * 20, 0);

        BukkitUtil.runTaskLaterAsync(60L, ()-> SendAnimationTitle(player, subtitle));
    }

    public void StartGameTitle(Player player, String subtitle) {
        String game = GameSettings.minigame.toString();
        String title = "§6§l" + game;
        String subTitle = (GameSettings.teamMode ? "§7Team" : "§7Solo");

        TITLE_API.sendTitle(player, title, subTitle, 0, 10 * 20, 0);

        BukkitUtil.runTaskLaterAsync(60L, ()-> SendAnimationTitle(player, subtitle));
    }

    private void SendAnimationTitle(Player player, String subtitle) {
        if (!player.isOnline())
            return;
        Language lang = GAMER_MANAGER.getGamer(player).getLanguage();
        List<String> list = StringUtil.getAnimationTitle(lang.getMessage( "START_TITLE_MSG"),
                "§c", "§e", 6);

        new BukkitRunnable() {
            int l = 0;
            @Override
            public void run() {
                TITLE_API.sendTitle(player, "§c§l" + list.get(this.l), subtitle,
                        0, 4 * 20, 4 * 20);
                this.l++;
                if (this.l == list.size())
                    cancel();
            }
        }.runTaskTimer(DartaAPI.getInstance(), 2L, 2L);
    }
}
