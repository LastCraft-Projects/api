package net.lastcraft.dartaapi.guis.basic.donate;

import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.guis.CustomItems;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import net.lastcraft.dartaapi.guis.basic.DonateGui;
import net.lastcraft.dartaapi.guis.basic.Gui;
import org.bukkit.Material;

public class GoldHelpGui extends Gui {

    public GoldHelpGui(GuiDefaultContainer container, Language lang) {
        super(container, lang, lang.getMessage("GUI_DONATE_GUI_NAME") + " Gold");
    }

    @Override
    protected void setItems() {
        dInventory.setItem(40, new DItem(CustomItems.getBack2(lang),
                (player, clickType, slot) -> {
                    SOUND_API.play(player, SoundType.PICKUP);
                    listener.openGui(DonateGui.class, player);
                }));

        dInventory.setItem(5, 2, new DItem(ItemUtil.getBuilder(Head.MAINLOBBY)
                .setName(lang.getMessage("GUI_DONATE_GUI_ITEM_NAME"))
                .setLore(lang.getList("GUI_DONATE_GOLD_ITEM_LORE"))
                .build()));

        dInventory.setItem(4, 3, new DItem(ItemUtil.getBuilder(Head.CREATIVE)
                .setName("§bCreative")
                .setLore(lang.getList("GUI_DONATE_GOLD_CREATIVE_LORE"))
                .build()));

        dInventory.setItem(5, 3, new DItem(ItemUtil.getBuilder(Material.GRASS)
                .setName("§bSkyBlock")
                .setLore(lang.getList("GUI_DONATE_GOLD_SKYBLOCK_LORE"))
                .build()));

        dInventory.setItem(6, 3, new DItem(ItemUtil.getBuilder(Material.IRON_PICKAXE)
                .setName("§bAnarchy")
                .setLore(lang.getList("GUI_DONATE_GOLD_ANARCHY_LORE"))
                .build()));
        dInventory.setItem(3, 3, new DItem(ItemUtil.getBuilder(Material.BED)
                .setName("§bBedWars")
                .setLore(lang.getList( "GUI_DONATE_GOLD_BEDWARS_LORE"))
                .build()));
        dInventory.setItem(7, 3, new DItem(ItemUtil.getBuilder(Material.BLAZE_POWDER)
                .setName("§bArcadeGames")
                .setLore(lang.getList( "GUI_DONATE_GOLD_ARCADEGAMES_LORE"))
                .build()));
    }
}
