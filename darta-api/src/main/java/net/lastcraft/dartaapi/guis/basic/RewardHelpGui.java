package net.lastcraft.dartaapi.guis.basic;

import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.guis.CustomItems;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import org.bukkit.Material;

public class RewardHelpGui extends Gui {

    public RewardHelpGui(GuiDefaultContainer listener, Language lang) {
        super(listener, lang, lang.getMessage("GUI_REWARDS_NAME"));
    }

    @Override
    protected void setItems() {
        dInventory.setItem(40, new DItem(CustomItems.getBack2(lang),
                (player, clickType, slot) -> {
            SOUND_API.play(player, SoundType.PICKUP);
            listener.openGui(HelpGui.class, player);
        }));

        dInventory.setItem(9 + 3 - 1, new DItem(ItemUtil.getBuilder(Head.BATMAN.getHead())
                .setName("§bKitWars")
                .setLore(lang.getList("GUI_REWARDS_ITEM_1_LORE"))
                .build()));

        dInventory.setItem(9 + 4 - 1, new DItem(ItemUtil.getBuilder(Material.ENDER_PEARL)
                .setName( "§bSkyWars")
                .setLore(lang.getList("GUI_REWARDS_ITEM_2_LORE"))
                .build()));
        dInventory.setItem(9 + 5 - 1, new DItem(ItemUtil.getBuilder(Head.LUCKYWARS.getHead())
                .setName("§bLuckyWars")
                .setLore(lang.getList( "GUI_REWARDS_ITEM_3_LORE"))
                .build()));
        dInventory.setItem(9 + 6 - 1, new DItem(ItemUtil.getBuilder(Material.BED)
                .setName("§bBedWars")
                .setLore(lang.getList( "GUI_REWARDS_ITEM_4_LORE"))
                .build()));
        dInventory.setItem(9 + 7 - 1, new DItem(ItemUtil.getBuilder(Material.DRAGON_EGG)
                .setName("§bEggWars")
                .setLore(lang.getList("GUI_REWARDS_ITEM_5_LORE"))
                .build()));

        dInventory.setItem(9 * 3 + 5 - 1, new DItem(ItemUtil.getBuilder(Material.BOOK)
                .setName(lang.getMessage( "GUI_REWARDS_ITEM_INFO_NAME"))
                .setLore(lang.getList("GUI_REWARDS_ITEM_INFO_LORE"))
                .build()));
    }
}
