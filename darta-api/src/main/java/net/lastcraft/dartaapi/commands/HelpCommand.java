package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import net.lastcraft.dartaapi.guis.basic.HelpGui;
import org.bukkit.entity.Player;

public final class HelpCommand implements CommandInterface {

    private final GuiDefaultContainer container;

    public HelpCommand(GuiDefaultContainer guiDefaultContainer) {
        this.container = guiDefaultContainer;
        SpigotCommand command = COMMANDS_API.register("help", this, "помощь");
        command.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;
        Player player = gamer.getPlayer();

        container.openGui(HelpGui.class, player);
    }
}
