package net.lastcraft.dartaapi.functions.compass;

import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@Deprecated//todo удалить или переписать потом
public class CompassListener extends DListener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR){
            return;
        }

        Player player = e.getPlayer();
        ItemStack itemInHand = player.getItemInHand();

        if (ItemUtil.compareItems(itemInHand, ItemsListener.getCompass())){

            e.setCancelled(true);

            Compass compass = CompassManager.getCompass(player);
            if (compass != null) {
                compass.setNewPlayer();
            }
        }
    }

}
