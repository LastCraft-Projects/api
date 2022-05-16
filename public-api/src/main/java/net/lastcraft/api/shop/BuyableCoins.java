package net.lastcraft.api.shop;

/**
 * Интерфейс, обозначающий, что предмет можно купить за монетки
 */
@Deprecated
public interface BuyableCoins extends Shopable {

    /**
     * @param playerName Имя игрока, который хочет купить
     * @return Цена предмета (в монетках)
     */
    int cost(String playerName);

    /**
     * Может ли игрок купить данный предмет, не считая денег
     * @param playerName Имя игрока, который хочет купить
     * @return Причина, по которой игрок не может купить данный предмет (null, если может)
     */
    String canBuy(String playerName);


    /**
     * Что происходит, когда игрок покупает предмет
     * @param playerName Имя игрока, который покупает предмет
     */
    void buy(String playerName);
}
