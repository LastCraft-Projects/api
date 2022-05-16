 package net.lastcraft.api.hologram;

import net.lastcraft.api.depend.PacketObject;
import net.lastcraft.api.hologram.lines.AnimationHoloLine;
import net.lastcraft.api.hologram.lines.ItemDropLine;
import net.lastcraft.api.hologram.lines.ItemFloatingLine;
import net.lastcraft.api.hologram.lines.TextHoloLine;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public interface Hologram extends PacketObject {

    AnimationHoloLine addAnimationLine(long speed, Supplier<String> replacerLine);
    TextHoloLine addTextLine(String text);
    ItemDropLine addDropLine(boolean pickup, ItemStack item);
    ItemFloatingLine addBigItemLine(boolean rotate, ItemStack item);

    TextHoloLine insertTextLine(int index, String text);
    ItemDropLine insertDropLine(int index, boolean pickup, ItemStack item);
    ItemFloatingLine insertBigItemLine(int index, boolean rotate, ItemStack item);

    List<HoloLine> getHoloLines();
    <T extends HoloLine> T getHoloLine(int index);

    void addTextLine(Collection<String> listText);

    int getSize();

    void removeLine(int index);
    void removeLine(HoloLine line);

    /**
     * Получить локацию энтити
     * @return - локация энтити
     */
    Location getLocation();

    /**
     * телепортировать энтити куда-то
     * @param location - куда телепортировать
     */
    void onTeleport(Location location);
}
