package net.lastcraft.dartaapi.game;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Random;

public class SettingsGameListener extends DListener {

    @EventHandler
    public void JoinPlayerEvent(PlayerJoinEvent e) {
        BukkitUtil.runTask(() -> {
            Player pl = e.getPlayer();
            BukkitGamer gamer = GAMER_MANAGER.getGamer(pl);

            if (gamer.isDiamond() && gamer.getSetting(SettingsType.FLY)) {
                pl.setAllowFlight(true);
                pl.setFlying(true);
            }

            if (GameSettings.minigame != MiniGameType.DEFAULT) {
                if (gamer.getSetting(SettingsType.MUSIC)) {
                    //TODO Сделать выбор музыки в донат меню
                    if (gamer.isEmerald() && (GameState.getCurrent() == GameState.WAITING || GameState.getCurrent() == GameState.STARTING)) {
                        Material sound = null;
                        int random = new Random().nextInt(8);
                        switch (random) {
                            case 0:
                                sound = Material.RECORD_3;
                                break;
                            case 1:
                                sound = Material.RECORD_4;
                                break;
                            case 2:
                                sound = Material.RECORD_5;
                                break;
                            case 3:
                                sound = Material.RECORD_6;
                                break;
                            case 4:
                                sound = Material.RECORD_7;
                                break;
                            case 5:
                                sound = Material.RECORD_8;
                                break;
                            case 6:
                                sound = Material.RECORD_9;
                                break;
                            case 7:
                                sound = Material.RECORD_10;
                                break;
                            case 8:
                                sound = Material.RECORD_12;
                                break;
                        }
                        pl.playEffect(pl.getLocation(), Effect.RECORD_PLAY, sound);
                    }
                }
            }
        });
    }
}
