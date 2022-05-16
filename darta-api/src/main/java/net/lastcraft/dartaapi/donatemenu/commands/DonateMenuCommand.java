package net.lastcraft.dartaapi.donatemenu.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.dartaapi.donatemenu.DonateMenuListener;
import net.lastcraft.dartaapi.donatemenu.guis.MainDonateMenuGui;
import org.bukkit.entity.Player;

public final class DonateMenuCommand implements CommandInterface {

    private final DonateMenuListener donateMenuListener;

    public DonateMenuCommand(DonateMenuListener donateMenuListener) {
        this.donateMenuListener = donateMenuListener;

        SpigotCommand command = COMMANDS_API.register("dm", this, "donatemenu");
        command.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;
        Player player = gamer.getPlayer();

        donateMenuListener.open(player, MainDonateMenuGui.class);
    }
}
