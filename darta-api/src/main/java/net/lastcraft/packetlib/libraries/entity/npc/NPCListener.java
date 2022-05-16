package net.lastcraft.packetlib.libraries.entity.npc;

import net.lastcraft.api.entity.npc.NPC;
import net.lastcraft.api.event.game.RestartGameEvent;
import net.lastcraft.api.event.gamer.GamerInteractNPCEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.packetlib.libraries.entity.EntityAPIImpl;
import net.lastcraft.packetlib.packetreader.event.AsyncPlayerInUseEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class NPCListener extends DListener<DartaAPI> {

    private final NPCManager npcManager;

    public NPCListener(EntityAPIImpl entityAPI) {
        super(entityAPI.getDartaAPI());

        this.npcManager = entityAPI.getNpcManager();
    }

    @EventHandler
    public void onInterractNPC(AsyncPlayerInUseEntityEvent e) {
        Player player = e.getPlayer();
        int entityId = e.getEntityId();

        NPC npc = npcManager.getNPCs().get(entityId);
        if (npc == null) {
            return;
        }

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) {
            return;
        }

        GamerInteractNPCEvent event = new GamerInteractNPCEvent(gamer, npc,
                (e.getAction() == AsyncPlayerInUseEntityEvent.Action.ATTACK
                        ? GamerInteractNPCEvent.Action.LEFT_CLICK
                        : GamerInteractNPCEvent.Action.RIGHT_CLICK));
        BukkitUtil.runTask(() -> BukkitUtil.callEvent(event));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        npcManager.removeFromTeams(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRestart(RestartGameEvent e) {
        npcManager.getNPCs().values().forEach(npc -> {
            if (npc.entity != null && !npc.getLocation().getWorld().getName()
                    .equals("lobby"))
                npc.remove();
        });
    }
}
