package net.lastcraft.dartaapi.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.lastcraft.api.event.gamer.GamerChangeGroupEvent;
import net.lastcraft.api.event.gamer.GamerChangeLanguageEvent;
import net.lastcraft.api.event.gamer.GamerChangePrefixEvent;
import net.lastcraft.api.event.gamer.GamerChangeSettingEvent;
import net.lastcraft.base.skin.SkinType;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.SkinUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.messaging.PluginMessageListener;

public final class BungeeMessageListener extends DListener<DartaAPI> implements PluginMessageListener {

    public BungeeMessageListener(DartaAPI dartaAPI) {
        super(dartaAPI);

        Bukkit.getMessenger().registerIncomingPluginChannel(dartaAPI, "LastSkins", this);

        initOutgoingChannel("LastGroup");
        initOutgoingChannel("LastLanguage");
        initOutgoingChannel("LastPrefix");
        initOutgoingChannel("LastSettings");
    }

    private void initOutgoingChannel(String name) {
        Bukkit.getMessenger().registerOutgoingPluginChannel(javaPlugin, name);
    }

    @EventHandler
    public void onChangeGroup(GamerChangeGroupEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        String name = player.getName();
        int groupID = e.getGroup().getId();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(name);
        out.writeInt(groupID);

        player.sendPluginMessage(javaPlugin, "LastGroup", out.toByteArray());
    }

    @EventHandler
    public void onChangeLang(GamerChangeLanguageEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(player.getName());
        out.writeInt(e.getLanguage().getId());
        out.writeInt(e.getOldLanguage().getId());

        player.sendPluginMessage(javaPlugin, "LastLanguage", out.toByteArray());
    }

    @EventHandler
    public void onChangePrefix(GamerChangePrefixEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        String name = player.getName();
        String prefix = e.getPrefix();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(name);
        out.writeUTF(prefix);

        player.sendPluginMessage(javaPlugin, "LastPrefix", out.toByteArray());
    }

    @EventHandler
    public void onChangeSettings(GamerChangeSettingEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null) {
            return;
        }

        String name = player.getName();
        int settingID = e.getSetting().getKey();
        boolean result = e.isResult();

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(name);
        out.writeInt(settingID);
        out.writeBoolean(result);

        player.sendPluginMessage(javaPlugin, "LastSettings", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equals("LastSkins")) {
            return;
        }

        ByteArrayDataInput input = ByteStreams.newDataInput(bytes);

        Player target = Bukkit.getPlayer(input.readUTF());
        if (target == null || !player.isOnline()) {
            return;
        }

        SkinUtil.setSkin(target, input.readUTF(), input.readUTF(), input.readUTF(), SkinType.getSkinType(input.readInt()));
    }
}
