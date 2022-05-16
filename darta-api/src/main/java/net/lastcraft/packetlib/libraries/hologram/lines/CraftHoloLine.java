package net.lastcraft.packetlib.libraries.hologram.lines;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.entity.EntityAPI;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.hologram.HoloLine;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.packetlib.libraries.hologram.CraftHologram;
import net.lastcraft.packetlib.libraries.hologram.HologramManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class CraftHoloLine implements HoloLine {

    private static final EntityAPI ENTITY_API = LastCraft.getEntityAPI();

    private final HologramManager hologramManager;
    protected final Hologram hologram;
    protected final CustomStand customStand;

    CraftHoloLine(CraftHologram hologram, Location location) {
        this.hologram = hologram;
        this.hologramManager = hologram.getHologramManager();

        customStand = ENTITY_API.createStand(location);
        customStand.setInvisible(true);
        customStand.setSmall(true);
        customStand.setBasePlate(false);

        hologramManager.addCustomStand(hologram, customStand);
    }

    public void remove() {
        hologramManager.removeCustomStand(customStand);
        customStand.remove();
    }

    @Override
    public CustomStand getCustomStand() {
        return customStand;
    }

    @Override
    public void delete() {
        hologram.removeLine(this);
    }

    public void teleport(Location location){
        customStand.onTeleport(location);
    }

    public void showTo(Player player) {
        customStand.showTo(player);
    }

    public void hideTo(Player player) {
        customStand.removeTo(player);
    }

    public void setPublic(boolean vision) {
        customStand.setPublic(vision);
    }

    public boolean isVisibleTo(Player player) {
        return customStand.isVisibleTo(player);
    }

}
