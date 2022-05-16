package net.lastcraft.api.command;

import net.lastcraft.api.player.GamerEntity;
import org.bukkit.command.Command;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CommandsAPI {
    /**
     * Регистрация новой команды
     * @param name название команды (без слеша)
     * @param aliases алиасы команды
     * @return SpigotCommand
     */
    SpigotCommand register(String name, CommandInterface commandInterface, String... aliases);

    /**
     * получить отсортированный список который надо показывать при нажатии таб
     * @param seeList - начальный список
     * @param args - аргументы команды
     * @return - отсортированный
     */
    List<String> getCompleteString(Collection<String> seeList, String[] args);

    /**
     * отключить команды
     * @param command - команда
     */
    void disableCommand(SpigotCommand command);
    void disableCommand(String name);
    void disableCommand(Command command);
    void disableCommands(Plugin plugin);

    /**
     * получить бакитовскую команду
     * @param name - имя команды
     * @return - команда
     */
    Command getCommand(String name);

    /**
     * получить все зареганные команды
     * @return - команды
     */
    Map<String, SpigotCommand> getCommands();

    /**
     * вывксти сообщение недостаточно аргументов
     * @param gamerEntity - кому
     * @param key - ключ локализации
     */
    void notEnoughArguments(GamerEntity gamerEntity, String key);

    /**
     * вывести сообщение игрок оффлайн
     * @param gamerEntity - кому
     * @param name - кто оффлайн
     */
    void playerOffline(GamerEntity gamerEntity, String name);

    /**
     * вывести сообщение игрок никогда не играл
     * @param gamerEntity - кому вывести
     * @param name - кто не играл
     */
    void playerNeverPlayed(GamerEntity gamerEntity, String name);

    /**
     * показать помощь по команде
     * @param gamerEntity - кому
     * @param command - команда
     * @param key - ключ локализации
     */
    void showHelp(GamerEntity gamerEntity, String command, String key);
}
