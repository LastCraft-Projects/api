package net.lastcraft.packetlib.libraries.hologram;

import lombok.Getter;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.hologram.HologramAPI;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HologramAPIImpl implements HologramAPI {

    private final DartaAPI dartaAPI;

    private final HologramManager hologramManager;

    public HologramAPIImpl(DartaAPI dartaAPI) {
        this.dartaAPI = dartaAPI;

        this.hologramManager = new HologramManager();

        new HologramListener(this);

        Bukkit.getScheduler().runTaskTimerAsynchronously(dartaAPI, new HologramTask(hologramManager), 0, 1L);
        //ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //executorService.scheduleAtFixedRate(new HologramTask(hologramManager), 0, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public Hologram createHologram(Location location) {
        return new CraftHologram(hologramManager, location);
    }

    @Override
    public List<Hologram> getHolograms() {
        return new ArrayList<>(hologramManager.getHolograms());
    }
}
