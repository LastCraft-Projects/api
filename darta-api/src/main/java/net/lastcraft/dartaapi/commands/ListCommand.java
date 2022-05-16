package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.types.GameType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;

public final class ListCommand implements CommandInterface {

    private final GamerManager gamerManager = LastCraft.getGamerManager();

    public ListCommand() {
        COMMANDS_API.register("list", this,
                "список", "игроки", "players");
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        StringBuilder list = new StringBuilder();

        int size = 0;
        for (BukkitGamer all : gamerManager.getGamers().values()) {
            list.append(all.getDisplayName()).append("§f, ");
            size++;
        }

        Language lang = gamerEntity.getLanguage();
        String prefix = "§6" + GameType.current.getName() + "§8| ";

        gamerEntity.sendMessageLocale("LIST_CMD",
                prefix,
                String.valueOf(size),
                StringUtil.getCorrectWord(size, "PLAYERS_1", lang),
                list.substring(0, list.length() - 4));
    }
}
