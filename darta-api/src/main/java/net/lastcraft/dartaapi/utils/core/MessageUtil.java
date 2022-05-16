package net.lastcraft.dartaapi.utils.core;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.player.Spigot;
import net.lastcraft.base.locale.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Deprecated
public class MessageUtil {
    private static final Spigot SPIGOT = LastCraft.getGamerManager().getSpigot();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    @Deprecated
    public static void broadcast(String text) {
        Bukkit.broadcastMessage(GameSettings.prefix + text);
    }

    public static void alertMsg(boolean gamePrefix, String text, Object... objects) {
        for (BukkitGamer gamer : GAMER_MANAGER.getGamers().values()) {
            Language lang = gamer.getLanguage();
            Player player = gamer.getPlayer();
            if (player == null || !player.isOnline()) continue;
            player.sendMessage((gamePrefix ? GameSettings.prefix : "") + lang.getMessage(text, objects));
        }
        Language langConsole = SPIGOT.getLanguage();
        broadcastConsole(gamePrefix, langConsole.getMessage(text, objects));
    }

    public static void alertMsg(boolean gamePrefix, String text) {
        for (BukkitGamer gamer : GAMER_MANAGER.getGamers().values()) {
            Language lang = gamer.getLanguage();
            Player player = gamer.getPlayer();
            if (player == null || !player.isOnline()) continue;
            player.sendMessage((gamePrefix ? GameSettings.prefix : "") + lang.getMessage(text));
        }
        Language langConsole = SPIGOT.getLanguage();
        broadcastConsole(gamePrefix, langConsole.getMessage(text));
    }

    private static void broadcastConsole(boolean prefix, String text) {
        SPIGOT.sendMessage((prefix ? GameSettings.prefix : "") + text);
    }

    public static void sendMessage(Player player, String text) {
        player.sendMessage(GameSettings.prefix + text);
    }

    public static void sendConsole(String text) {
        SPIGOT.sendMessage("§6DartaAPI §8| §f" + text);
    }

    public static void sendConsoleError(String text) {
        SPIGOT.sendMessage("§6DartaAPI §8| §c" + text);
    }

    public static void sendListToPlayers(String message, boolean center) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer == null) continue;
            Language lang = gamer.getLanguage();
            for (String string : lang.getList(message)) {
                if (center) {
                    player.sendMessage(StringUtil.stringToCenter(string));
                } else {
                    player.sendMessage(string);
                }
            }
        }
        Language langConsole = SPIGOT.getLanguage();
        for (String string : langConsole.getList(message)) {
            MessageUtil.broadcastConsole(false, string);
        }
    }
}
