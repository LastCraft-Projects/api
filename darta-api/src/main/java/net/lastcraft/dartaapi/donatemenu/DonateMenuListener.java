package net.lastcraft.dartaapi.donatemenu;

import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.dartaapi.donatemenu.commands.DonateMenuCommand;
import net.lastcraft.dartaapi.donatemenu.commands.FastMessageCommand;
import net.lastcraft.dartaapi.donatemenu.commands.PrefixCommand;
import net.lastcraft.dartaapi.donatemenu.event.AsyncGamerSendFastMessageEvent;
import net.lastcraft.dartaapi.donatemenu.guis.DonateMenuGui;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class DonateMenuListener extends DListener<DartaAPI> {

    private final Map<String, DonateMenuData> data = new ConcurrentHashMap<>();

    public DonateMenuListener(DartaAPI dartaAPI) {
        super(dartaAPI);

        new DonateMenuCommand(this);
        new PrefixCommand(this);
        new FastMessageCommand(this);
    }

    public void open(Player player, Class<? extends DonateMenuGui> clazz) {
        String name = player.getName().toLowerCase();
        DonateMenuData data = this.data.get(name);
        if (data == null) {
            data = new DonateMenuData(player);
            this.data.put(name, data);
        }

        DonateMenuGui gui = data.get(clazz);
        if (gui == null) {
            return;
        }

        gui.open();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        data.remove(player.getName().toLowerCase());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSendFM(AsyncGamerSendFastMessageEvent e) {
        BukkitGamer gamer = e.getGamer();

        FastMessage fastMessage = e.getFastMessage();
        e.getRecipients().forEach(otherGamer -> sendFastMessage(gamer, otherGamer, fastMessage));
        sendFastMessage(gamer, GAMER_MANAGER.getSpigot(), fastMessage);
    }

    private void sendFastMessage(BukkitGamer gamer, GamerEntity gamerEntity, FastMessage fastMessage) {
        gamerEntity.sendMessage(" " + gamer.getChatName() + " §f✎§e "
                + gamerEntity.getLanguage().getMessage(fastMessage.getKey()) + " " + fastMessage.getSmile());
    }
}
