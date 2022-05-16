package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.Group;
import org.bukkit.Sound;

@Deprecated //todo удалить потом
public class PlayMusicCommand implements CommandInterface {

    public PlayMusicCommand() {
        SpigotCommand command = COMMANDS_API.register("playmusic", this);
        command.setMinimalGroup(Group.ADMIN);
        command.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;
        if (args.length < 3) {
            gamer.sendMessage("Не хватает аргументов! <music> <volume> <pitch>");
            return;
        }

        try {
            Sound sound = Sound.valueOf(args[0]);
            gamer.playSound(sound, Float.parseFloat(args[1]), Float.parseFloat(args[2]));
        } catch (Exception e) {
            gamer.sendMessage("Что-то обосралось!");
        }

    }
}
