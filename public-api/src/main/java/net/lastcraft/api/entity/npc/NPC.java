package net.lastcraft.api.entity.npc;

import net.lastcraft.api.entity.PacketEntityLiving;

public interface NPC extends PacketEntityLiving {

    String getName();

    /**
     * HeadLook означает, что НПС будет всегда следить за игроком(кроме спектаторов) - по умолчанию
     * true
     *
     * @return следит или нет
     */
    boolean isHeadLook();

    void setHeadLook(boolean flag);

    /**
     * анимация
     *
     * @param animation - анимация
     */
    void animation(AnimationNpcType animation);

    /**
     * тип NPC
     *
     * @return - тип НПС
     */
    NpcType getType();

    boolean isGlowing();
    /**
     * задать обводку вокруг энтити
     * @param glowing - да или нет
     */
    void setGlowing(boolean glowing);
}
