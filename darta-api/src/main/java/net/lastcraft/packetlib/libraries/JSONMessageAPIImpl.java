package net.lastcraft.packetlib.libraries;

import net.lastcraft.api.JSONMessageAPI;
import net.lastcraft.base.util.JsonBuilder;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.types.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

public class JSONMessageAPIImpl implements JSONMessageAPI {

    private final PacketContainer container = NmsAPI.getManager().getPacketContainer();

    @Override
    public void send(Player player, String json) {
        container.sendChatPacket(player, json, ChatMessageType.SYSTEM);
    }

    @Override
    public void send(Player player, JsonBuilder jsonBuilder) {
        send(player, jsonBuilder.toString());
    }

    @Override
    public void send(Player player, BaseComponent... components) {
        player.spigot().sendMessage(components);
    }
}
