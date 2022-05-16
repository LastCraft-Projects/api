package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.command.SpigotCommand;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;

public final class MoneyCommand implements CommandInterface {

    public MoneyCommand() {
        SpigotCommand spigotCommand = COMMANDS_API.register("money", this,
                "balance", "bal");
        spigotCommand.setOnlyPlayers(true);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        BukkitGamer gamer = (BukkitGamer) gamerEntity;

        Language lang = gamer.getLanguage();

        String end = StringUtil.getCorrectWord(gamer.getMoney(PurchaseType.MYSTERY_DUST), "MONEY_1", lang);
        gamerEntity.sendMessageLocale("BALANCE",
                StringUtil.getNumberFormat(gamer.getMoney(PurchaseType.MYSTERY_DUST)),
                end);
    }
}