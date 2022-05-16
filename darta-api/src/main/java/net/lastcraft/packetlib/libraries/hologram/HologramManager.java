package net.lastcraft.packetlib.libraries.hologram;

import io.netty.util.internal.ConcurrentSet;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.packetlib.libraries.hologram.lines.CraftHoloLine;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class HologramManager {

    private final Set<CraftHologram> holograms = new ConcurrentSet<>();
    private final Map<CustomStand, Hologram> holograms_by_stand = new ConcurrentHashMap<>();

    void addHologram(CraftHologram hologram) {
        holograms.add(hologram);
    }

    public void addCustomStand(Hologram hologram, CustomStand customStand) {
        holograms_by_stand.put(customStand, hologram);
    }

    public void removeCustomStand(CustomStand customStand) {
        holograms_by_stand.remove(customStand);
    }

    Set<CraftHologram> getHolograms() {
        return holograms;
    }

    Map<CustomStand, Hologram> getHologramByStand() {
        return holograms_by_stand;
    }

    void removeHologram(CraftHologram hologram) {
        holograms.remove(hologram);
        for (CraftHoloLine craftHoloLine : hologram.getLines()){
            Bukkit.getOnlinePlayers().forEach(craftHoloLine::hideTo);
            craftHoloLine.remove();
        }
    }
}
