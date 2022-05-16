package net.lastcraft.dartaapi.commands;

import net.lastcraft.api.JSONMessageAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.command.CommandInterface;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerEntity;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.JsonBuilder;
import org.bukkit.entity.Player;

public final class RulesCommand implements CommandInterface {

    private final String url = "https://lastcraft.net/threads/Правила-для-серверов-lastcraft.5/#post-12";
    private final JSONMessageAPI jsonMessageAPI = LastCraft.getJsonMessageAPI();

    public RulesCommand() {
        COMMANDS_API.register("rules", this);
    }

    @Override
    public void execute(GamerEntity gamerEntity, String command, String[] args) {
        Language lang = gamerEntity.getLanguage();
        Player player = ((BukkitGamer) gamerEntity).getPlayer();

        jsonMessageAPI.send(player, new JsonBuilder()
                .addText(lang.getMessage( "RULES_COMMAND"))
                .addOpenUrl("§d" + url, url, lang.getMessage("RULES_COMMAND_HOVER"))
                .toString());
    }
}
