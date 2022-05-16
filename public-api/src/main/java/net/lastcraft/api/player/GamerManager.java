package net.lastcraft.api.player;

import net.lastcraft.base.gamer.IBaseGamer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Map;

public interface GamerManager {

    @Nullable
    GamerEntity getEntity(CommandSender sender);

    Spigot getSpigot();

    @Nullable
    BukkitGamer getGamer(String name);
    @Nullable
    BukkitGamer getGamer(Player player);
    @Nullable
    BukkitGamer getGamer(int playerID);

    void removeGamer(String name);
    void removeGamer(Player player);
    void removeGamer(BukkitGamer gamer);

    boolean containsGamer(Player player);
    boolean containsGamer(String name);

    Map<String, GamerEntity> getGamerEntities();
    Map<String, BukkitGamer> getGamers();

    /**
     * полуить онлайн игрока или оффлайн
     * @param playerID - ник или айди игрока
     * @return - игрок
     * ВНИМАНИЕ! Делать ассинхронно
     */
    @Nullable
    IBaseGamer getOrCreate(int playerID);
    @Nullable
    IBaseGamer getOrCreate(String name);
}
