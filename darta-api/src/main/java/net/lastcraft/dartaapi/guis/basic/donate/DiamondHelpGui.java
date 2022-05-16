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

public class DiamondHelpGui extends Gui {

    public DiamondHelpGui(GuiDefaultContainer container, Language lang) {
        super(container, lang, lang.getMessage( "GUI_DONATE_GUI_NAME") + " Diamond");
    }

    @Override
    protected void setItems() {
        dInventory.setItem(40, new DItem(CustomItems.getBack2(lang),
                (player, clickType, slot) -> {
                    SOUND_API.play(player, SoundType.PICKUP);
                    listener.openGui(DonateGui.class, player);
                }));

        dInventory.setItem(5, 2, new DItem(ItemUtil.getBuilder(Head.MAINLOBBY)
                .setName(lang.getMessage( "GUI_DONATE_GUI_ITEM_NAME"))
                .setLore(lang.getList("GUI_DONATE_DIAMOND_ITEM_LORE"))
                .build()));

        dInventory.setItem(3, 3, new DItem(ItemUtil.getBuilder(Material.BED)
                .setName("§bBedWars")
                .setLore(lang.getList( "GUI_DONATE_DIAMOND_BEDWARS_LORE"))
                .build()));

        dInventory.setItem(4, 3, new DItem(ItemUtil.getBuilder(Material.DRAGON_EGG)
                .setName("§bEggWars")
                .setLore(lang.getList( "GUI_DONATE_DIAMOND_EGGWARS_LORE"))
                .build()));

        dInventory.setItem(5, 3, new DItem(ItemUtil.getBuilder(Head.CREATIVE)
                .setName("§bCreative")
                .setLore(lang.getList("GUI_DONATE_DIAMOND_CREATIVE_LORE"))
                .build()));

        dInventory.setItem(6, 3, new DItem(ItemUtil.getBuilder(Material.GRASS)
                .setName("§bSkyBlock")
                .setLore(lang.getList("GUI_DONATE_DIAMOND_SKYBLOCK_LORE"))
                .build()));

        dInventory.setItem(7, 3, new DItem(ItemUtil.getBuilder(Material.IRON_PICKAXE)
                .setName("§bAnarchy")
                .setLore(lang.getList("GUI_DONATE_DIAMOND_ANARCHY_LORE"))
                .build()));

        dInventory.setItem(3, 4, new DItem(ItemUtil.getBuilder(Material.BLAZE_POWDER)
                .setName("§bArcadeGames")
                .setLore(lang.getList( "GUI_DONATE_DIAMOND_ARCADEGAMES_LORE"))
                .build()));
    }
}
