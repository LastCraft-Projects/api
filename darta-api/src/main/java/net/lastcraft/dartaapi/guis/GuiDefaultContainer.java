package net.lastcraft.dartaapi.guis;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.commands.DonateCommand;
import net.lastcraft.dartaapi.commands.HelpCommand;
import net.lastcraft.dartaapi.guis.basic.DonateGui;
import net.lastcraft.dartaapi.guis.basic.Gui;
import net.lastcraft.dartaapi.guis.basic.HelpGui;
import net.lastcraft.dartaapi.guis.basic.RewardHelpGui;
import net.lastcraft.dartaapi.guis.basic.donate.*;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuiDefaultContainer {

    private final GamerManager gamerManager = LastCraft.getGamerManager();
    private final Map<String, Map<Integer, Gui>> guis = new ConcurrentHashMap<>();

    public GuiDefaultContainer() {
        for (Language language : Language.values()) {
            addGui(HelpGui.class, language);
            addGui(DonateGui.class, language);
            addGui(RewardHelpGui.class, language);
            addGui(GoldHelpGui.class, language);
            addGui(DiamondHelpGui.class, language);
            addGui(EmeraldHelpGui.class, language);
            addGui(MagmaHelpGui.class, language);
            addGui(ShulkerHelpGui.class, language);
        }

        new HelpCommand(this);
        new DonateCommand(this);
    }

    private void addGui(Class<? extends Gui> clazz, Language language) {
        String name = clazz.getSimpleName().toLowerCase();
        try {
            Gui gui = clazz.getConstructor(GuiDefaultContainer.class, Language.class).newInstance(this, language);
            Map<Integer, Gui> guis = this.guis.computeIfAbsent(name, k -> new HashMap<>());
            guis.put(language.getId(), gui);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openGui(Class<? extends Gui> clazzGui, Player player) {
        String name = clazzGui.getSimpleName().toLowerCase();
        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer == null)
            return;

        Map<Integer, Gui> guis = this.guis.get(name);
        if (guis == null)
            return;

        Gui gui = guis.getOrDefault(gamer.getLanguage().getId(),
                guis.get(Language.getDefault().getId()));
        gui.getInventory().openInventory(player);
    }

}
