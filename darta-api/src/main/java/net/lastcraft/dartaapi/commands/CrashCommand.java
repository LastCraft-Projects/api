package net.lastcraft.dartaapi.commands;

import com.google.common.collect.ImmutableList;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.CommandTabComplete;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public final class CrashCommand implements CommandInterface, CommandTabComplete {

    private final NmsManager nmsManager = NmsAPI.getManager();

    public CrashCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("crash", this);
        spigotCommand.setGroup(Group.ADMIN);
        spigotCommand.setCommandTabComplete(this);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        if (args.length != 1) {
            COMMANDS_API.notEnoughArguments(gamerEntity, "CRASH_FORMAT");
            return;
        }

        String name = args[0];
        Player target = Bukkit.getPlayer(name);
        if (target == null || !target.isOnline()) {
            COMMANDS_API.playerOffline(gamerEntity, name);
            return;
        }

        if (gamerEntity.getName().equals(name)) {
            gamerEntity.sendMessageLocale("CRASH_ERROR_YOU");
            return;
        }

        gamerEntity.sendMessageLocale("CRASH_PLAYER", target.getDisplayName());
        nmsManager.sendCrashClientPacket(target);

        //PacketPlayOutExplosion packet = new PacketPlayOutExplosion(Double.MAX_VALUE, Double.MAX_VALUE,
        //        Double.MAX_VALUE, Float.MAX_VALUE, Collections.EMPTY_LIST, newlocale CraftVec3D(Double.MAX_VALUE,
        //        Double.MAX_VALUE, Double.MAX_VALUE));
        //Packets.sendPacket(target, packet);
    }

    @Override
    public List<String> getComplete(GamerEntity gamerEntity, String alias, String[] args) {
        if (gamerEntity.isHuman() && ((BukkitGamer)gamerEntity).getGroup() != Group.ADMIN) {
            return ImmutableList.of();
        }

        if (args.length == 1) {
            List<String> names = Bukkit.getOnlinePlayers()
                    .stream()
                    .map(HumanEntity::getName)
                    .collect(Collectors.toList());
            return COMMANDS_API.getCompleteString(names, args);
        }

        return ImmutableList.of();
    }
}
