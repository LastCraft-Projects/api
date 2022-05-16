package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import net.lastcraft.dartaapi.guis.basic.DonateGui;

public final class DonateCommand implements CommandInterface {

    private final GuiDefaultContainer container;

    public DonateCommand(GuiDefaultContainer container) {
        this.container = container;
        SpigotCommand command = COMMANDS_API.register("donate", this,
                "donat", "донат", "дон");
        command.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        container.openGui(DonateGui.class, ((BukkitGamer)gamerEntity).getPlayer());
    }
}
