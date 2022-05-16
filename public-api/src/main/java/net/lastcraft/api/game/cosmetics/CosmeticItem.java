package net.lastcraft.api.game.cosmetics;

@Deprecated
public interface CosmeticItem {

    /**
     * Получение номера предмета
     * @return номер предмета
     */
    int getId();

    /**
     * Получение типа предмета
     * @return тип предмета
     */
    int getType();
}
