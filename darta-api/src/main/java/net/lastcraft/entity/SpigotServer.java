package net.lastcraft.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.player.Spigot;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
@AllArgsConstructor
public final class SpigotServer implements Spigot {

    @Getter
    private final DartaAPI mainPlugin;
    private final String name;

    @Override
    public CommandSender getCommandSender() {
        return mainPlugin.getServer().getConsoleSender();
    }

    @Override
    public void sendMessage(String message) {
        getCommandSender().sendMessage(message);
    }

    @Override
    public void sendMessages(List<String> messages) {
        messages.forEach(this::sendMessage);
    }

    @Override
    public void sendMessageLocale(String key, Object... objects) {
        sendMessage(getLanguage().getMessage(key, objects));
    }

    @Override
    public void sendMessagesLocale(String key, Object... objects) {
        sendMessages(getLanguage().getList(key, objects));
    }

    @Override
    public Language getLanguage() {
        return Language.getDefault();
    }

    @Override
    public String getChatName() {
        return "console-" + name;
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public void broadcast(String message) {
        sendMessage(message);
        GamerAPI.getGamers().values().forEach(gamer -> gamer.sendMessage(message));
    }

    @Override
    public void broadcastLocale(String key, Object... objects) {
        sendMessageLocale(key, objects);
        GamerAPI.getGamers().values().forEach(gamer -> gamer.sendMessageLocale(key, objects));
    }
}
