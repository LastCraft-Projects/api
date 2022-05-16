package net.lastcraft.entity;

import lombok.Getter;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.gamer.GamerBase;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.dartaapi.loader.DartaAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public final class DartaGamerManager implements GamerManager {

    @Getter
    private final SpigotServer spigot;

    public DartaGamerManager(DartaAPI dartaAPI) {
        spigot = new SpigotServer(dartaAPI, dartaAPI.getUsername());
    }

    @Override
    public GamerEntity getEntity(CommandSender sender) {
        if (sender instanceof Player) {
            return getGamer(sender.getName());
        } else if (sender instanceof ConsoleCommandSender) {
            return spigot;
        } else {
            return null;
        }
    }

    @Override
    public BukkitGamer getGamer(String name) {
        return (BukkitGamer) GamerAPI.get(name);
    }

    @Override
    public BukkitGamer getGamer(Player player) {
        if (player == null) {
            return null;
        }
        return getGamer(player.getName());
    }

    @Override
    public BukkitGamer getGamer(int playerID) {
        return (BukkitGamer) GamerAPI.isOnline(playerID);
    }

    @Override
    public void removeGamer(String name) {
        GamerAPI.removeGamer(name.toLowerCase());
    }

    @Override
    public void removeGamer(Player player) {
        removeGamer(player.getName());
    }

    @Override
    public void removeGamer(BukkitGamer gamer) {
        removeGamer(gamer.getName());
    }

    @Override
    public boolean containsGamer(Player player) {
        return containsGamer(player.getName());
    }

    @Override
    public boolean containsGamer(String name) {
        return GamerAPI.contains(name);
    }

    @Override
    public Map<String, GamerEntity> getGamerEntities() {
        Map<String, GamerEntity> gamerEntities = new HashMap<>();
        gamerEntities.put(spigot.getName(), spigot);
        for (GamerBase gamerBase : GamerAPI.getGamers().values()) {
            gamerEntities.put(gamerBase.getName(), (BukkitGamer) gamerBase);
        }

        return gamerEntities;
    }

    @Override
    public Map<String, BukkitGamer> getGamers() {
        Map<String, BukkitGamer> gamers = new HashMap<>();
        for (GamerBase gamerBase : GamerAPI.getGamers().values()) {
            gamers.put(gamerBase.getName().toLowerCase(), (BukkitGamer) gamerBase);
        }
        return gamers;
    }

    @Override
    public IBaseGamer getOrCreate(int playerID) {
        IBaseGamer gamer = GamerAPI.isOnline(playerID);
        if (gamer != null) {
            return gamer;
        }
        return GamerAPI.get(playerID);
    }

    @Override
    public IBaseGamer getOrCreate(String name) {
         return GamerAPI.getOrCreate(name);
     }
}
