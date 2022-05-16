package net.lastcraft.api;

import org.bukkit.entity.Player;

public interface CoreAPI {

    /**
     * Получить название этого сервера
     * @return название данного сервера
     */
    String getServerName();

    void sendToServer(Player player, String server);

    void sendToHub(Player player);

    /**
     * получить онлайн сервера или списка серверов
     * @param namePattern - имя сервера или регикс
     * @return - онлайн
     */
    int getOnline(String namePattern); //@hub or hub-1
}
