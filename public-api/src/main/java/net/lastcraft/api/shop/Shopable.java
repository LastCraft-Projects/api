package net.lastcraft.api.shop;

import org.bukkit.inventory.ItemStack;

@Deprecated
public interface Shopable {

    /**
     * @param playerName Имя игрока
     * @return Иконка предмета, с описанием и названием
     */
    ItemStack getIcon(String playerName);


    /**
     * @param playerName Имя игрока
     * @return Есть ли данный предмет у игрока
     */
    boolean have(String playerName);

}
