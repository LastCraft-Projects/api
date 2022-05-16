package net.lastcraft.dartaapi.functions.compass;

import net.lastcraft.api.ActionBarAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.utils.bukkit.LocationUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Deprecated
public class CompassTask extends Thread {

    private static final ActionBarAPI ACTION_BAR_API = LastCraft.getActionBarAPI();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    CompassTask(){
        start();
    }

    public void run(){
        while (!Thread.interrupted()){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Compass compass : CompassManager.getCompasses()) {
                Player player = compass.getPlayer();
                if (PlayerUtil.isAlive(player)) {
                    Language lang = GAMER_MANAGER.getGamer(player).getLanguage();
                    Player currentPlayer = compass.getCurrentPlayer();
                    if (currentPlayer != null)
                        player.setCompassTarget(currentPlayer.getLocation());

                    if (ItemUtil.compareItems(player.getItemInHand(), ItemsListener.getCompass())){
                        if (currentPlayer != null) {
                            String message = lang.getMessage( "COMPASS_MESSAGE", currentPlayer.getDisplayName());
                            double distance = LocationUtil.distance(player.getLocation(), currentPlayer.getLocation());
                            if (distance == -1){
                                message += lang.getMessage("COMPASS_MESSAGE_ERROR1");
                            } else {
                                BigDecimal bd = new BigDecimal(distance);
                                bd = bd.setScale(1, RoundingMode.HALF_UP);
                                message += "§a" + bd.doubleValue();
                            }
                            ACTION_BAR_API.sendBar(player, message);
                        } else {
                            ACTION_BAR_API.sendBar(player, lang.getMessage("COMPASS_MESSAGE_ERROR2"));
                        }
                    }
                } else {
                    CompassManager.removeCompass(player);
                }

            }
        }

    }

}
