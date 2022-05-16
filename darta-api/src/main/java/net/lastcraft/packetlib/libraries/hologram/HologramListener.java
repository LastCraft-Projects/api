package net.lastcraft.packetlib.libraries.hologram;

import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.api.event.game.RestartGameEvent;
import net.lastcraft.api.event.gamer.GamerInteractCustomStandEvent;
import net.lastcraft.api.event.gamer.GamerInteractHologramEvent;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class HologramListener extends DListener<DartaAPI> {

    private final HologramManager hologramManager;

    HologramListener(HologramAPIImpl hologramAPI) {
        super(hologramAPI.getDartaAPI());

        this.hologramManager = hologramAPI.getHologramManager();
    }

    @EventHandler
    public void onPlayerInteractCustomStand(GamerInteractCustomStandEvent e) {
        CustomStand stand = e.getStand();
        BukkitGamer gamer = e.getGamer();

        GamerInteractCustomStandEvent.Action action = e.getAction();

        Hologram hologram = hologramManager.getHologramByStand().get(stand);
        if (hologram == null)
            return;

        GamerInteractHologramEvent event = new GamerInteractHologramEvent(gamer, hologram, action);
        BukkitUtil.callEvent(event);
    }

    @EventHandler
    public void onRestart(RestartGameEvent e) {
        for (Hologram hologram : hologramManager.getHolograms())
            if (!hologram.getLocation().getWorld().getName().equalsIgnoreCase("lobby"))
                hologram.remove();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(AsyncGamerQuitEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        hologramManager.getHolograms().forEach(hologram -> {
            hologram.removeTo(player);
            if (hologram.getOwner() != null && player.getName().equals(hologram.getOwner().getName()))
                hologram.remove();
        });
    }
}
