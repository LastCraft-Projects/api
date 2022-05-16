package net.lastcraft.dartaapi.donatemenu.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.donatemenu.DonateMenuListener;
import net.lastcraft.dartaapi.donatemenu.FastMessage;
import net.lastcraft.dartaapi.donatemenu.guis.FastMessageGui;
import org.bukkit.entity.Player;

import java.util.Map;

public final class FastMessageCommand implements CommandInterface {

    private final DonateMenuListener donateMenuListener;

    public FastMessageCommand(DonateMenuListener donateMenuListener) {
        this.donateMenuListener = donateMenuListener;

        SpigotCommand command = COMMANDS_API.register("fastmessage", this, "fm");
        command.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;
        Player player = gamer.getPlayer();

        if (args.length == 0) {
            donateMenuListener.open(player, FastMessageGui.class);
            return;
        }

        if (!gamer.isDiamond()) {
            gamer.sendMessageLocale("NO_PERMS_GROUP", Group.DIAMOND.getNameEn());
            return;
        }

        FastMessage fastMessage = null;
        for (Map.Entry<String, FastMessage> entry : FastMessage.getMessages(gamerEntity.getLanguage()).entrySet()) {
            String name = entry.getKey().toLowerCase();
            if (name.startsWith(args[0].toLowerCase())) {
                fastMessage = entry.getValue();
                break;
            }
        }

        if (fastMessage == null) {
            gamerEntity.sendMessageLocale("MESSAGE_NOT_FOUND", args[0]);
            return;
        }

        fastMessage.sendToAll(gamer);
    }
}
