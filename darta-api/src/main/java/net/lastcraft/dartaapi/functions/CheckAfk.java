package net.lastcraft.dartaapi.functions;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.TitleAPI;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CheckAfk extends DListener {

    private final Map<String, BukkitTask> afk = new ConcurrentHashMap<>();
    private final SoundAPI soundAPI = LastCraft.getSoundAPI();
    private final TitleAPI titleAPI = LastCraft.getTitlesAPI();
    private final GamerManager gamerManager = LastCraft.getGamerManager();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (GameSettings.minigame != MiniGameType.DEFAULT && GameSettings.minigame != MiniGameType.SURVIVAL) {
            Player player = e.getPlayer();
            String name = player.getName();
            BukkitGamer gamer = gamerManager.getGamer(player);
            if (gamer == null)
                return;
            Language lang = gamer.getLanguage();
            if (e.getFrom().getBlockX() != e.getTo().getBlockX()
                    || e.getFrom().getBlockY() != e.getTo().getBlockY()
                    || e.getFrom().getBlockZ() != e.getTo().getBlockZ()) {
                BukkitTask task = afk.remove(name);
                if (task != null)
                    task.cancel();
                return;
            }

            if (afk.containsKey(name))
                return;
            if (PlayerUtil.isSpectator(player))
                return;
            BukkitTask active = new BukkitRunnable() {
                int timeAFK = 0;
                int timeTitle = 0;
                @Override
                public void run() {
                    if (GameState.getCurrent() == GameState.GAME) {
                        if (!player.isOnline()) {
                            cancel();
                            afk.remove(name);
                        }
                        if (timeAFK >= 2700) {
                            soundAPI.play(player, SoundType.AFK_SOUND);
                            if (timeTitle >= 10) {
                                timeTitle = 0;
                                titleAPI.sendTitle(player, "§r", lang.getMessage("AFK"),
                                        0, 3, 1);
                            }
                            timeTitle++;
                        }
                        timeAFK++;
                    }
                }
            }.runTaskTimer(DartaAPI.getInstance(), 1L, 1L);
            afk.put(name, active);
        }
    }
}