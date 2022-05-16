package net.lastcraft.dartaapi.game.depend;

import io.netty.util.internal.ConcurrentSet;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.module.WaitModule;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Deprecated
public class LoginListener extends DListener {

    private static Set<Player> players = new ConcurrentSet<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLogin(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED)
            return;

        Player player = e.getPlayer();
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;
        Language lang = gamer.getLanguage();

        if (GameState.WAITING == GameState.getCurrent() || GameState.STARTING == GameState.getCurrent()) {
            if (WaitModule.getTime() >= 4 || !WaitModule.isStarting()) {
                List<Player> players = new ArrayList<>(LoginListener.players);
                if (players.size() >= GameSettings.slots) {
                    if (gamer.isGold()) {
                        Collections.shuffle(players);
                        for (Player all : players) {
                            if (all == player)
                                continue;
                            if (GAMER_MANAGER.getGamer(all).getGroup() == Group.DEFAULT) {
                                LoginListener.players.remove(all);
                                all.sendMessage(lang.getMessage("SLOT_BUSY", (gamer.getPrefix() + player.getName())));
                                PlayerUtil.redirectToHub(all);
                                BukkitUtil.runTaskLater(20, ()-> all.kickPlayer(lang.getMessage("ERROR_TELEPORT")));
                                return;
                            }
                        }
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, lang.getMessage("ONLY_DONATOR"));
                        GamerAPI.removeGamer(gamer);
                    } else {
                        e.disallow(PlayerLoginEvent.Result.KICK_FULL, lang.getMessage("ARENA_BUSY"));
                        GamerAPI.removeGamer(gamer);
                    }
                }
            } else {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, lang.getMessage("ARENA_START"));
            }
        } else if (GameState.GAME == GameState.getCurrent() && System.currentTimeMillis() - GameState.getGameTime() <= 5000) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, lang.getMessage("ARENA_START"));
            GamerAPI.removeGamer(gamer);
        } else if (GameState.END == GameState.getCurrent()) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, lang.getMessage( "ARENA_RESTART"));
            GamerAPI.removeGamer(gamer);
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        players.add(e.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        players.remove(e.getPlayer());
    }
}