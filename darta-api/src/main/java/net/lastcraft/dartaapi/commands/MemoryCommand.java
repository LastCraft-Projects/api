package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.lang.management.ManagementFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public final class MemoryCommand implements CommandInterface {

    private final String spigotName = LastCraft.getGamerManager().getSpigot().getName();

    public MemoryCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("memory", this, "mem");
        spigotCommand.setMinimalGroup(Group.ADMIN);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        Runtime runtime = Runtime.getRuntime();
        gamerEntity.sendMessage("Память сервера §c§l" + spigotName + "§f:");
        gamerEntity.sendMessage(" §c▪ §fАптайм: - §7"
                + TimeUtil.leftTime(Language.RUSSIAN,
                System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime()));
        gamerEntity.sendMessage(" §c▪ §fМаксимально - §7" + runtime.maxMemory() / 1048576L + " МБ");
        gamerEntity.sendMessage(" §c▪ §fВыделено - §a" + runtime.totalMemory() / 1048576L + " МБ");
        gamerEntity.sendMessage(" §c▪ §fСвободно - §e" + runtime.freeMemory() / 1048576L + " МБ");
        gamerEntity.sendMessage(" §c▪ §fИспользуется - §c" + (runtime.totalMemory() - runtime.freeMemory())
                / 1048576L + " МБ");
        gamerEntity.sendMessage(" ");

        if (!LastCraft.isMisc()) {
            return;
        }

        for (World world : Bukkit.getWorlds()) {
            AtomicInteger tileEntities = new AtomicInteger();

            try {
                for (Chunk chunk : world.getLoadedChunks()) {
                    tileEntities.addAndGet(chunk.getTileEntities().length);
                }

            } catch (java.lang.ClassCastException ex) {
                Bukkit.getLogger().log(Level.SEVERE, "Corrupted chunk achievement on world " + world, ex);
            }

            gamerEntity.sendMessage(" §c" + world.getName());
            gamerEntity.sendMessage("  §fЭнтити: §a" + world.getEntities().size());
            gamerEntity.sendMessage("  §fЧанков: §a" + world.getLoadedChunks().length);
            gamerEntity.sendMessage("  §fТайлов: §a" + tileEntities);
        }


    }
}
