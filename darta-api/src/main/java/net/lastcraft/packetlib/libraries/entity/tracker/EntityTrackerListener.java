package net.lastcraft.packetlib.libraries.entity.tracker;

import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.util.LocationUtil;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.packetlib.packetreader.event.AsyncChunkSendEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EntityTrackerListener extends DListener<DartaAPI> {

    private final TrackerManager trackerManager;

    public EntityTrackerListener(DartaAPI dartaAPI, TrackerManager trackerManager) {
        super(dartaAPI);
        this.trackerManager = trackerManager;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            for (BukkitGamer gamer : GAMER_MANAGER.getGamers().values()) {
                String name = gamer.getName().toLowerCase();
                Player player = gamer.getPlayer();
                if (player == null || !player.isOnline()) {
                    continue;
                }

                for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
                    if (!trackerEntity.isHeadLook()) {
                        continue;
                    }

                    Location location = trackerEntity.getLocation();
                    double distance = LocationUtil.distance(location, player.getLocation());
                    if (location.getWorld() == player.getWorld() && trackerEntity.canSee(player)) {
                        Set<String> names = trackerEntity.getHeadPlayers();
                        if (distance < 5 && distance != -1) {
                            names.add(name);
                            location = LocationUtil.faceEntity(trackerEntity.getLocation(), player).clone();
                            trackerEntity.sendHeadRotation(player, location.getYaw(), location.getPitch());
                        } else if (names.contains(name)) {
                            names.remove(name);
                            trackerEntity.sendHeadRotation(player, location.getYaw(), location.getPitch());
                        }

                    }
                }
            }
        }, 5, 50, TimeUnit.MILLISECONDS);
    }

    @EventHandler
    public void onJoin(AsyncChunkSendEvent e) {
        Player player = e.getPlayer();
        if (player == null)
            return;

        String worldName = e.getWorldName();
        int x = e.getX();
        int z = e.getZ();

        for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) { //todo подобное есть в АПИ для ViaVersion
            if (!trackerEntity.getLocation().getWorld().getName().equalsIgnoreCase(worldName)) {
                continue;
            }

            int trackedEntityX = trackerEntity.getLocation().getBlockX() >> 4;
            int trackedEntityZ = trackerEntity.getLocation().getBlockZ() >> 4;
            if (trackedEntityX == x && trackedEntityZ == z && trackerEntity.canSee(player)) {
                trackerEntity.destroy(player);
                BukkitUtil.runTaskLaterAsync(10L, () -> trackerEntity.spawn(player));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(AsyncGamerQuitEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
            trackerEntity.removeTo(player);
            if (trackerEntity.getOwner() != null && player.getName().equals(trackerEntity.getOwner().getName()))
                trackerEntity.remove();
        }
    }

    /*
    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();

        BukkitUtil.runTaskAsync(() -> {
            for (TrackerEntity trackerEntity : trackerManager.getTrackerEntities()) {
                if (trackerEntity.getLocation().getWorld() == e.getFrom() && trackerEntity.canSee(player)) {
                    trackerEntity.destroy(player);
                }
            }
        });
    }
    */
}
