package net.lastcraft.api.entity.npc.types;

import net.lastcraft.api.entity.npc.NPC;

public interface SlimeNPC extends NPC {

    /**
     * узнать размер слайма
     * @return - размер
     */
    int getSize();

    /**
     * установить размер слайма
     * только для SLIME
     */
    void setSize(int size);
}
