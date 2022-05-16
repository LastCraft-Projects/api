package net.lastcraft.dartaapi.utils.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.List;

@Deprecated
public class LocationUtil {

    public static Location getCenter(List<Location> locations) {
        double x = 0;
        double y = 0;
        double z = 0;

        for (Location location : locations) {
            x += location.getX();
            y += location.getY();
            z += location.getZ();
        }

        int size = locations.size();

        return new Location(locations.stream().findFirst().get().getWorld(), x / size, y / size, z / size);
    }

    public static Location faceEntity(Location location, Entity entity) {
        Vector direction = location.toVector().subtract(entity.getLocation().toVector());
        direction.multiply(-1);
        location.setDirection(direction);
        return location;
    }

    public static double distance(Location location1, Location location2){
        if (location1.getWorld().equals(location2.getWorld())){
            return location1.distance(location2);
        }
        return -1;
    }

    public static Location stringToLocation(String loc, boolean pitchAndYaw) {
        String[] locSplit = loc.split(";");
        Location location = new Location(Bukkit.getWorld(locSplit[0]), Double.parseDouble(locSplit[1]), Double.parseDouble(locSplit[2]), Double.parseDouble(locSplit[3]));
        if (pitchAndYaw) {
            if (locSplit.length == 6) {
                location.setPitch(Float.parseFloat(locSplit[4]));
                location.setYaw(Float.parseFloat(locSplit[5]));
            }
        }
        return location;
    }

    public static String locationToString(Location loc, boolean pitchAndYaw) {
        String location = loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ();
        if (pitchAndYaw) {
            location += ";" + loc.getPitch() + ";" + loc.getYaw();
        }
        return location;
    }

    public static void loadChunk(Location location) {
        if (!location.getChunk().isLoaded()) {
            location.getChunk().load();
        }
    }

    public static boolean compareLocations(Location loc1, Location loc2) {
        return loc1.getWorld().getName().equals(loc2.getWorld().getName())
                && loc1.getBlockX() == loc2.getBlockX()
                && loc1.getBlockY() == loc2.getBlockY()
                && loc1.getBlockZ() == loc2.getBlockZ();
    }

    public static Location getSecondBedLocation(Location bedLocation) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    Block bedBlock = bedLocation.getBlock().getRelative(x, y, z);
                    if (!(bedLocation.equals(bedBlock.getLocation()))) {
                        if (bedBlock.getType().equals(Material.BED_BLOCK)) {
                            return bedBlock.getLocation();
                        }
                    }
                }
            }
        }

        return null;
    }
}
