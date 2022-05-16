package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameState;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

@Deprecated
public class DamageListener extends DListener {

    private final NmsManager nmsManager = NmsAPI.getManager();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        e.setCancelled(true);
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (GameState.getCurrent() == GameState.GAME)
                return;
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                nmsManager.disableFire(player);
            }
        }
    }
}
