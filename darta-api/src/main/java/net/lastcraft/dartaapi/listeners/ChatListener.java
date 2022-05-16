package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.gamer.async.AsyncGamerChatFormatEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.Spigot;
import net.lastcraft.api.util.ChatUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.Set;

public final class ChatListener extends DListener<DartaAPI> {

    private final Spigot spigot = LastCraft.getGamerManager().getSpigot();

    public ChatListener(DartaAPI dartaAPI) {
        super(dartaAPI);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) {
            return;
        }

        String suffix = gamer.getGroup().getSuffix();

        String prefix = gamer.getPrefix();
        e.setFormat(" §r" + prefix + player.getName() + suffix + " %2$s");

        if (PlayerUtil.isSpectator(player)){
            e.getRecipients().clear();
            e.setFormat(" §8[§4✖§8]" + e.getFormat());
            e.getRecipients().addAll(PlayerUtil.getSpectators());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);

        BukkitGamer sender = GAMER_MANAGER.getGamer(e.getPlayer());
        if (sender == null) {
            return;
        }

        Set<BukkitGamer> recipients = new HashSet<>();
        for (Player player : e.getRecipients()) {
            BukkitGamer otherGamer = GAMER_MANAGER.getGamer(player);
            if (otherGamer == null) {
                continue;
            }

            recipients.add(otherGamer);
        }

        AsyncGamerChatFormatEvent event = new AsyncGamerChatFormatEvent(
                sender,
                recipients,
                e.getFormat(),
                e.getMessage());
        BukkitUtil.callEvent(event);

        String message = event.getMessage();
        spigot.sendMessage(event.getBaseFormat().replace("%2$s", "") + message);
        for (BukkitGamer gamer : event.getRecipients()) {
            String eventFormat = event.getFormat(gamer).replace("%2$s", "");

            gamer.sendMessage(eventFormat + message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChatToPlayer(AsyncGamerChatFormatEvent e) {
        BukkitGamer gamerSender = e.getGamer();

        String suffix = gamerSender.getGroup().getSuffix();
        ChatColor color = ChatColor.getByChar(suffix.charAt(suffix.length() - 1));

        for (BukkitGamer gamer : new HashSet<>(e.getRecipients())) {
            if (gamer == gamerSender || !gamer.getSetting(SettingsType.CHAT)) {
                continue;
            }

            boolean nicknameFound = false;

            TextComponent finalComponent = new TextComponent(e.getFormat(gamer).replace("%2$s", ""));

            for (String word : e.getMessage().split(" ")) {
                if (!nicknameFound && word.equalsIgnoreCase(gamer.getName())) {
                    nicknameFound = true;

                    TextComponent component = new TextComponent(gamer.getName());
                    component.setColor(ChatColor.LIGHT_PURPLE);
                    component.setUnderlined(true);

                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatUtil.getComponentFromList(
                            gamer.getLanguage().getList("HOVER_MESSAGE_CHAT", gamerSender.getDisplayName())))
                    }));

                    component.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + gamerSender.getName() + " "));

                    finalComponent.addExtra(component);

                } else {
                    TextComponent textComponent = new TextComponent(word);
                    if (color != null) {
                        textComponent.setColor(color);
                    }
                    finalComponent.addExtra(textComponent);
                }

                finalComponent.addExtra(" ");
            }

            if (nicknameFound) {
                e.removeRecipient(gamer);

                gamer.playSound(SoundType.LEVEL_UP, 0.6f, 0.2f);

                gamer.sendMessage(finalComponent);
            }
        }
    }
}
