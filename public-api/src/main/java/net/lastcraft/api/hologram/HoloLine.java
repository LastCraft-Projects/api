package net.lastcraft.api.hologram;

import net.lastcraft.api.entity.stand.CustomStand;

public interface HoloLine {

    CustomStand getCustomStand();

    /**
     * удалить строчку
     */
    void delete();
}
