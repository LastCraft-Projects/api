package net.lastcraft.dartaapi.donatemenu.guis;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.donatemenu.DonateMenuData;
import net.lastcraft.dartaapi.guis.CustomItems;
import org.bukkit.entity.Player;

public final class MainDonateMenuGui extends DonateMenuGui {

    public MainDonateMenuGui(Player player, DonateMenuData donateMenuData, Language language) {
        super(player, donateMenuData, language.getMessage("DONATE_MENU_GUI"));
    }

    @Override
    protected void setItems(BukkitGamer gamer) {
        Language lang = gamer.getLanguage();

        if (LastCraft.isHub() || LastCraft.isLobby()) {
            inventory.setItem(40, new DItem(CustomItems.getBack(lang),
                    (player, clickType, slot) -> {
                SOUND_API.play(player, SoundType.PICKUP);
                player.chat("/profile");
            }));
        }

        inventory.setItem(11, new DItem(ItemUtil.getBuilder(Head.BOOKS)
                .setName("§b" + lang.getMessage( "DONATE_MENU_FAST_MESSAGE_NAME"))
                .setLore(lang.getList("DONATE_MENU_FAST_MESSAGE_LORE"))
                .removeFlags()
                .build(), (player, clickType, slot) -> {
            FastMessageGui gui = donateMenuData.get(FastMessageGui.class);
            if (gui != null) {
                gui.open();
            }
        }));

        inventory.setItem(12, new DItem(ItemUtil.getBuilder(Head.PREFIX)
                .setName("§b" + lang.getMessage( "DONATE_MENU_PREFIX_NAME"))
                .setLore(lang.getList("DONATE_MENU_PREFIX_LORE", gamer.getChatName()))
                .removeFlags()
                .build(), (player, clickType, slot) -> {
            PrefixGui gui = donateMenuData.get(PrefixGui.class);
            if (gui != null) {
                gui.open();
            }
        }));

        inventory.setItem(13, new DItem(ItemUtil.getBuilder(Head.JOIN_MESSAGE)
                .setName("§b" + lang.getMessage( "DONATE_MENU_JOIN_MESSAGE_NAME"))
                .setLore(lang.getList("DONATE_MENU_JOIN_MESSAGE_LORE"))
                .removeFlags()
                .build(), (player, clickType, slot) -> {
            JoinMessageGui gui = donateMenuData.get(JoinMessageGui.class);
            if (gui != null) {
                gui.open();
            }
        }));

    }
}
