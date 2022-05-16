package net.lastcraft.api.entity.npc.types;

import net.lastcraft.api.entity.npc.NPC;

public interface CreeperNPC extends NPC {

    /**
     * надутый или нет
     * @return - крипер
     */
    boolean isPowered();

    /**
     * сделать надутым
     * @param flag - какой крипер сейчас
     */
    void setPowered(boolean flag);
}
