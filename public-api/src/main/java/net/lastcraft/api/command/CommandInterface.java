package net.lastcraft.api.command;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerEntity;

public interface CommandInterface {

    /**
     * Выполнение команды
     * @param gamerEntity - объект того, кто исполняет
     * @param command - то, что ввели
     * @param args - аргументы(кроме самой команды)
     */
    void execute(GamerEntity gamerEntity, String command, String[] args);

    /**
     * Получить интерфейс для регистрации команд
     *
     * @return интерфейс
     */
    CommandsAPI COMMANDS_API = LastCraft.getCommandsAPI();

}
