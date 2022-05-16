package net.lastcraft.dartaapi.guis.basic;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.guis.CustomItems;
import net.lastcraft.dartaapi.guis.GuiDefaultContainer;
import net.lastcraft.dartaapi.guis.basic.donate.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DonateGui extends Gui {

    public DonateGui(GuiDefaultContainer listener, Language lang) {
        super(listener, lang, lang.getMessage( "GUI_DONATE_MAIN_NAME"));
        dInventory.createInventoryAction(new InventoryAction() {
            @Override
            public void onOpen(Player player) {
                if (LastCraft.isGame() || LastCraft.isMisc()) {
                    return;
                }
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
        dInventory.setItem(9 + 3 - 1, new DItem(ItemUtil.getBuilder(Head.SHULKER_DONATE.getHead())
                .setName("§b" + lang.getMessage( "GUI_DONATE_ITEM_1_NAME") + " §7§lSHULKER")
                .setLore(lang.getList("GUI_DONATE_ITEM_1_LORE", 2100))
                .build(), (clicker, clickType, slot) -> listener.openGui(ShulkerHelpGui.class, clicker)));

        dInventory.setItem(9 + 4 - 1, new DItem(ItemUtil.getBuilder(Head.MAGMA_DONATE.getHead())
                .setName("§b" + lang.getMessage("GUI_DONATE_ITEM_1_NAME") + " §c§lMAGMA")
                .setLore(lang.getList("GUI_DONATE_ITEM_1_LORE", 900))
                .build(), (clicker, clickType, slot) -> listener.openGui(MagmaHelpGui.class, clicker)));

        dInventory.setItem(9 + 5 - 1, new DItem(ItemUtil.getBuilder(Head.EMERALD_DONATE.getHead())
                .setName("§b" + lang.getMessage("GUI_DONATE_ITEM_1_NAME") + " §a§lEMERALD")
                .setLore(lang.getList("GUI_DONATE_ITEM_1_LORE", 590))
                .build(), (clicker, clickType, slot) -> listener.openGui(EmeraldHelpGui.class, clicker)));

        dInventory.setItem(9 + 6 - 1, new DItem(ItemUtil.getBuilder(Head.DIAMOND_DONATE.getHead())
                .setName("§b" + lang.getMessage("GUI_DONATE_ITEM_1_NAME") + " §b§lDIAMOND")
                .setLore(lang.getList("GUI_DONATE_ITEM_1_LORE", 300))
                .build(), (clicker, clickType, slot) -> listener.openGui(DiamondHelpGui.class, clicker)));

        dInventory.setItem(9 + 7 - 1, new DItem(ItemUtil.getBuilder(Head.GOLD_DONATE.getHead())
                .setName("§b" + lang.getMessage( "GUI_DONATE_ITEM_1_NAME") + " §e§lGOLD")
                .setLore(lang.getList("GUI_DONATE_ITEM_1_LORE", 100))
                .build(), (clicker, clickType, slot) -> listener.openGui(GoldHelpGui.class, clicker)));

        dInventory.setItem(9 * 3 + 4 - 1, new DItem(ItemUtil.getBuilder(Material.EXP_BOTTLE)
                .setName(lang.getMessage("GUI_DONATE_ITEM_2_NAME"))
                .setLore(lang.getList( "GUI_DONATE_ITEM_2_LORE"))
                .build()));

        dInventory.setItem(9 * 3 + 6 - 1, new DItem(ItemUtil.getBuilder(Material.BOOK)
                .setName(lang.getMessage( "GUI_DONATE_ITEM_3_NAME"))
                .setLore(lang.getList("GUI_DONATE_ITEM_3_LORE"))
                .build()));
    }
}
