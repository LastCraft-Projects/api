package net.lastcraft.packetlib.libraries;

import net.lastcraft.api.CoreAPI;
import net.lastcraft.base.util.Cooldown;
import net.lastcraft.connector.bukkit.BukkitConnector;
import net.lastcraft.connector.bukkit.ConnectorPlugin;
import net.lastcraft.connector.bukkit.onlinecalc.OnlineCalculator;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.entity.Player;

public class CoreAPIImpl implements CoreAPI {

    private final DartaAPI dartaAPI;
    private final BukkitConnector bukkitConnector;
    private final OnlineCalculator onlineCalculator;

    public CoreAPIImpl(DartaAPI dartaAPI) {
        this.dartaAPI = dartaAPI;
        this.bukkitConnector = ConnectorPlugin.instance().getConnector();
        this.onlineCalculator = bukkitConnector.getOnlineCalculator();
    }

    @Override
    public String getServerName() {
        return dartaAPI.getUsername();
    }

    @Override
    public void sendToServer(Player player, String server) {
        if (Cooldown.hasCooldown(player.getName(), "redirect_players")) {
            return;
        }

        Cooldown.addCooldown(player.getName(), "redirect_players", 10);
        try {
            bukkitConnector.redirect(player, server);
        } catch (Exception ignored) {}
    }

    @Override
    public void sendToHub(Player player) {
        sendToServer(player, "@hub");
    }

    @Override
    public int getOnline(String namePattern) {
        if (!onlineCalculator.wasScheduled(namePattern)) {
            onlineCalculator.scheduleRequest(namePattern, 5, 0);
        }

        return onlineCalculator.getCachedOnline(namePattern);
    }
}
