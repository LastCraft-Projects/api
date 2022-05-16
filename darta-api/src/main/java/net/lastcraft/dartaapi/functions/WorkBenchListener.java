package net.lastcraft.dartaapi.functions;

import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class WorkBenchListener extends DListener {

    @EventHandler
    public void onWorkBenchCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/wb") || e.getMessage().equalsIgnoreCase("/workbench")){
            Player player = e.getPlayer();
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer.getGameMode() != GameModeType.SPECTATOR) {
                if (gamer.isAdmin()) {
                    player.openWorkbench(null, true);
                    e.setCancelled(true);
                } else {
                    gamer.sendMessageLocale("NO_PERMS_GROUP", Group.GOLD.getNameEn());
                    e.setCancelled(true);
                }
            } else {
                gamer.sendMessageLocale("ERROR_COMMAND_IN_GAME");
                e.setCancelled(true);
            }
        }
    }
}
