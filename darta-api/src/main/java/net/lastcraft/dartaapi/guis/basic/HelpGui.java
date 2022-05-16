package net.lastcraft.dartaapi.guis.basic;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.guis.CustomItems;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HelpGui extends Gui {

    public HelpGui(GuiDefaultContainer listener, Language lang) {
        super(listener, lang, lang.getMessage("GUI_HELP_NAME"));

        dInventory.createInventoryAction(new InventoryAction() {
            @Override
            public void onOpen(Player player) {
                if (LastCraft.isGame() || LastCraft.isMisc())
                    return;
                dInventory.setItem(40, new DItem(CustomItems.getBack2(lang),
                        (player1, clickType, slot) -> {
                    SOUND_API.play(player, SoundType.PICKUP);
                    player.chat("/profile");
                }));
            }
        });
    }

    @Override
    protected void setItems() {
        dInventory.setItem(9 + 3 - 1, new DItem(ItemUtil.getBuilder(Material.COMPASS)
                .setName(lang.getMessage("GUI_HELP_ITEM_1_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_1_LORE"))
                .build()));

        dInventory.setItem(9 + 4 - 1, new DItem(ItemUtil.getBuilder(Material.ENCHANTMENT_TABLE)
                        .setName(lang.getMessage("GUI_HELP_ITEM_2_NAME"))
                        .setLore(lang.getList("GUI_HELP_ITEM_2_LORE"))
                        .build(),
                (player, clickType, slot) -> listener.openGui(DonateGui.class, player)));

        dInventory.setItem(9 + 5 - 1, new DItem(ItemUtil.getBuilder(Head.JAKE.getHead())
                .setName(lang.getMessage("GUI_HELP_ITEM_3_NAME"))
                .setLore(lang.getList("GUI_HELP_ITEM_3_LORE"))
                .build()));

        dInventory.setItem(9 + 6 - 1, new DItem(ItemUtil.getBuilder(Material.PAINTING)
                .setName(lang.getMessage( "GUI_HELP_ITEM_4_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_4_LORE"))
                .build()));

        dInventory.setItem(9 + 7 - 1, new DItem(ItemUtil.getBuilder(Material.ARMOR_STAND)
                .setName(lang.getMessage("GUI_HELP_ITEM_5_NAME"))
                .setLore(lang.getList("GUI_HELP_ITEM_5_LORE"))
                .build()));

        dInventory.setItem(9 * 2 + 3 - 1, new DItem(ItemUtil.getBuilder(Material.BOOK_AND_QUILL)
                .setName(lang.getMessage("GUI_HELP_ITEM_6_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_6_LORE"))
                .build()));

        dInventory.setItem(9 * 2 + 4 - 1, new DItem(ItemUtil.getBuilder(Head.COMPUTER)
                .setName(lang.getMessage( "GUI_HELP_ITEM_7_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_7_LORE"))
                .build(), (clicker, clickType, slot) -> {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(clicker);
            if (gamer == null)
                return;
            clicker.closeInventory();
            gamer.sendMessagesLocale("GUI_HELP_ITEM_7_LORE");
        }));

        dInventory.setItem(9 * 2 + 6 - 1, new DItem(ItemUtil.getBuilder(Head.PARTY)
                .setName(lang.getMessage( "GUI_HELP_ITEM_9_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_9_LORE"))
                .build()));

        dInventory.setItem(9 * 2 + 7 - 1, new DItem(ItemUtil.getBuilder(Head.MONKEY)
                .setName(lang.getMessage("GUI_HELP_ITEM_10_NAME"))
                .setLore(lang.getList( "GUI_HELP_ITEM_10_LORE"))
                .build()));

        dInventory.setItem(9 * 2 + 5 - 1, new DItem(ItemUtil.getBuilder(Head.SHOP)
                        .setName(lang.getMessage("GUI_HELP_ITEM_8_NAME"))
                        .setLore(lang.getList("GUI_HELP_ITEM_8_LORE"))
                        .build(),
                (player, clickType, slot) -> listener.openGui(RewardHelpGui.class, player)));


    }
}
