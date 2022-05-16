package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.JoinMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinListener extends DListener<JavaPlugin> {

    public JoinListener(JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @EventHandler
    public void onJoin(AsyncGamerJoinEvent e) {
        BukkitGamer gamer = e.getGamer();
        if (gamer.getName().equalsIgnoreCase("IBROI")) {
            return;
        }
        JoinMessage joinMessage = gamer.getJoinMessage();
        if (joinMessage == null) {
            return;
        }

        if (joinMessage == JoinMessage.DEFAULT_MESSAGE) {
            GAMER_MANAGER.getGamerEntities().values().forEach(gamerEntity ->
                    gamerEntity.sendMessageLocale("JOIN_PLAYER_LO_LOBBY", gamer.getChatName()));
            return;
        }

        GAMER_MANAGER.getGamerEntities().values().forEach(gamerEntity ->
                gamerEntity.sendMessage(String.format(joinMessage.getMessage(), gamer.getChatName())));
    }
}
