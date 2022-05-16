package net.lastcraft.packetlib.libraries.entity.customstand;

import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.event.game.RestartGameEvent;
import net.lastcraft.api.event.gamer.GamerInteractCustomStandEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.packetlib.libraries.entity.EntityAPIImpl;
import net.lastcraft.packetlib.packetreader.event.AsyncPlayerInUseEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;

public class StandListener extends DListener<DartaAPI> {

    private final StandManager standManager;

    public StandListener(EntityAPIImpl entityAPI) {
        super(entityAPI.getDartaAPI());

        this.standManager = entityAPI.getStandManager();
    }

    @EventHandler
    public void onInteract(AsyncPlayerInUseEntityEvent e) {
        Player player = e.getPlayer();
        int entityId = e.getEntityId();

        CustomStand stand = standManager.getStand(entityId);
        if (stand == null) {
            return;
        }

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) {
            return;
        }

        GamerInteractCustomStandEvent event = new GamerInteractCustomStandEvent(gamer, stand,
                (e.getAction() == AsyncPlayerInUseEntityEvent.Action.ATTACK
                        ? GamerInteractCustomStandEvent.Action.LEFT_CLICK
                        : GamerInteractCustomStandEvent.Action.RIGHT_CLICK));
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRestart(RestartGameEvent e) {
        standManager.getStands().values().forEach(stand -> {
            if (!stand.getLocation().getWorld().getName().equals("lobby"))
                stand.remove();
        });
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        standManager.getStands().values().forEach(stand -> stand.destroy(player));
    }
}
