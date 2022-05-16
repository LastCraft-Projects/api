package net.lastcraft.packetlib.libraries.hologram.lines;

import lombok.Getter;
import net.lastcraft.api.hologram.lines.TextHoloLine;
import net.lastcraft.packetlib.libraries.hologram.CraftHologram;
import org.bukkit.Location;

@Getter
public class CraftTextHoloLine extends CraftHoloLine implements TextHoloLine {

    private String text;

    public CraftTextHoloLine(CraftHologram hologram, Location location, String text) {
        super(hologram, location);
        this.text = text;
        customStand.setCustomName(text);
    }

    @Override
    public void setText(String text) {
        this.text = text;
        customStand.setCustomName(text);
    }
}
