package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;

@Deprecated //todo удалить наверно из-за нового гейм АПИ
public final class LeaveCommand implements CommandInterface {

    public LeaveCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("leave", this);
        spigotCommand.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        Player player = ((BukkitGamer) gamerEntity).getPlayer();

        PlayerUtil.redirectToHub(player);
    }
}
