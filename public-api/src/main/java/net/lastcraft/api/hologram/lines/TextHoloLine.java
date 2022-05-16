package net.lastcraft.api.hologram.lines;

import net.lastcraft.api.hologram.HoloLine;

public interface TextHoloLine extends HoloLine {

    void setText(String text);

    String getText();
}
