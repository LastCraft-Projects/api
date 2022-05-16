package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.util.StringUtil;

public final class LevelCommand implements CommandInterface {

    public LevelCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("level", this);
        spigotCommand.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;

        gamerEntity.sendMessage(" ");
        gamerEntity.sendMessageLocale("LEVEL_COMMAND_1",
                StringUtil.getNumberFormat(gamer.getLevelNetwork()));
        gamerEntity.sendMessageLocale( "LEVEL_COMMAND_2",
                StringUtil.getNumberFormat(gamer.getExpNextLevel()));
        gamerEntity.sendMessage(" ");
    }

}
