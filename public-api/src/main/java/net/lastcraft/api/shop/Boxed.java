package net.lastcraft.api.shop;

import net.lastcraft.api.util.Rarity;

/**
 * Интерфейс, обозначающий, что предмет можно выбить из кейса
 */
@Deprecated
public interface Boxed extends Shopable {

    /**
     * @return Редкость предмета
     */
    Rarity getRarity();
}
