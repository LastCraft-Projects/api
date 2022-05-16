package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.effect.ParticleAPI;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.Group;

public final class FwCommand implements CommandInterface {

    private final ParticleAPI particleAPI = LastCraft.getParticleAPI();

    public FwCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("fireworks", this, "fw");
        spigotCommand.setGroup(Group.GOLD);
        spigotCommand.setOnlyGame(true);
        spigotCommand.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;

        particleAPI.shootRandomFirework(gamer.getPlayer());
        gamerEntity.sendMessageLocale("FW");
    }
}