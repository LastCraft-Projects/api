package net.lastcraft.api.entity.npc.types;

import net.lastcraft.api.entity.npc.NPC;

public interface ZombieNPC extends NPC {
    boolean isBaby();

    void setBaby(boolean baby);
}
