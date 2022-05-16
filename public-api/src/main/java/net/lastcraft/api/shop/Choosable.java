package net.lastcraft.api.shop;

@Deprecated
public interface Choosable extends Shopable {

    /**
     * Выбираем текущий предмет игроку.
     * Если предмет уже выбран - отменяем его или делаем что-то другое.
     * @param playerName Имя игрока
     */
    void choose(String playerName);

    /**
     * Проверяем, выбран ли текущий предмет
     * @param playerName Имя игрока
     * @return Выбран или нет
     */
    boolean isChoosed(String playerName);

}
