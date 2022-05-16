package net.lastcraft.api.manager;

import org.bukkit.entity.Player;

import java.util.Map;

public interface GuiManager<G> {

    /**
     * создать гуи
     * @param clazz - класс созданного гуи
     */
    void createGui(Class<? extends G> clazz);

    /**
     * удалить всем это гуи и отключить его
     * @param clazz - класс гуи, что удаляется
     */
    void removeGui(Class<? extends G> clazz);

    /**
     * получить гуи у игрока или создатиь новое
     * @param clazz - класс гуи
     * @param player - для кого получить гуи
     * @return - объект гуи
     */
    <T extends G> T getGui(Class<T> clazz, Player player);

    /**
     * при выходе игрока удалить ему все гуи в памяти
     * @param player - игрок которому удалять
     */
    void removeALL(Player player);

    Map<String, Map<String, G>> getPlayerGuis();
}
