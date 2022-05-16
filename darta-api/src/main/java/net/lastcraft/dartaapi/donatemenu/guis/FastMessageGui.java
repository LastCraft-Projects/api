package net.lastcraft.dartaapi.donatemenu.guis;

import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.donatemenu.DonateMenuData;
import net.lastcraft.dartaapi.donatemenu.FastMessage;
import org.bukkit.entity.Player;

import java.util.Map;

public final class FastMessageGui extends DonateMenuGui {

    public FastMessageGui(Player player, DonateMenuData donateMenuData, Language language) {
        super(player, donateMenuData, language.getMessage("DONATE_MENU_GUI")
                + " ▸ " + language.getMessage("DONATE_MENU_FAST_MESSAGE_NAME"));
    }

    @Override
    protected void setItems(BukkitGamer gamer) {
        setBack(donateMenuData.get(MainDonateMenuGui.class));

        Language lang = gamer.getLanguage();
        boolean enable = gamer.isDiamond();

        int slot = 10;
        int page = 0;
        for (Map.Entry<String, FastMessage> entry : FastMessage.getMessages(lang).entrySet()) {
            String name = entry.getKey();
            FastMessage fm = entry.getValue();

            inventory.setItem(page, slot++, new DItem(ItemUtil
                    .getBuilder(enable ? Head.BOOKS.getHead() : NO_PERMS.clone())
                    .setName((enable ? "§b" : "§c") + name)
                    .setLore(lang.getMessage("FAST_MESSAGE_LORE1", name + " " + fm.getSmile()))
                    .addLore(enable ?
                            lang.getList("FAST_MESSAGE_LORE2") :
                            lang.getList("LOBBY_SETTINGS_UNAVAILABLE", fm.getGroup().getNameEn()))
                    .build(), (clicker, clickType, slot1) -> {
                if (!enable) {
                    SOUND_API.play(clicker, SoundType.NO);
                    return;
                }
                clicker.chat("/fm " + name);
                player.closeInventory();
            }));

            if ((slot - 8) % 9 == 0) {
                slot += 2;
            }

            if (slot >= 35) {
                slot = 10;
                page++;
            }
        }
    }
}
