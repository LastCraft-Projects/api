package net.lastcraft.dartaapi.donatemenu.guis;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.sql.GlobalLoader;
import net.lastcraft.dartaapi.donatemenu.DonateMenuData;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public final class PrefixGui extends DonateMenuGui {

    private static final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();

    public PrefixGui(Player player, DonateMenuData donateMenuData, Language language) {
        super(player, donateMenuData, language.getMessage("DONATE_MENU_GUI")
                + " ▸ " + language.getMessage("DONATE_MENU_PREFIX_NAME"));
    }

    @Override
    protected void setItems(BukkitGamer gamer) {
        createItem(gamer,11, ChatColor.GRAY, 8);
        createItem(gamer,12, ChatColor.YELLOW, 4);
        createItem(gamer,13, ChatColor.AQUA, 3);
        createItem(gamer,14, ChatColor.GREEN, 5);
        createItem(gamer,15, ChatColor.RED, 14);
        createItem(gamer,20, ChatColor.DARK_GREEN, 13);
        createItem(gamer,21, ChatColor.GOLD, 1);
        createItem(gamer,22, ChatColor.DARK_AQUA, 9);
        createItem(gamer,23, ChatColor.LIGHT_PURPLE, 6);

        setBack(donateMenuData.get(MainDonateMenuGui.class));
    }

    private void createItem(BukkitGamer owner, int slot, ChatColor color, int blockColorId) {
        boolean available = owner.getPrefixColor() == color;
        Language lang = owner.getLanguage();
        String nameColor = "§" + color.getChar() + lang.getMessage(color.name().toUpperCase());
        inventory.setItem(slot, new DItem(ItemUtil.getBuilder(Material.STAINED_GLASS)
                .setDurability((short) blockColorId)
                .setName(nameColor)
                .setLore(available ?
                        lang.getList("PREFIX_CHANGE_LORE2") :
                        lang.getList("PREFIX_CHANGE_LORE", nameColor))
                .removeFlags()
                .glowing(available)
                .build(), (clicker, clickType, slot1) -> {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(clicker);
            if (gamer == null || (gamer.getGroup() != Group.SHULKER && gamer.getGroup() != Group.ADMIN) || gamer.isTester()) {
                player.sendMessage(lang.getMessage("NO_PERMS_SHULKER_ONLY"));
                SOUND_API.play(player, SoundType.NO);
                return;
            }

            if (available) {
                SOUND_API.play(player, SoundType.NO);
                return;
            }

            setPrefix(gamer, color);
            player.closeInventory();
        }));
    }

    private static void setPrefix(BukkitGamer gamer, ChatColor chatColor) {
        if (LastCraft.isGame()) {
            gamer.sendMessageLocale("NO_PREFIX_SET");
            SOUND_API.play(gamer.getPlayer(), SoundType.NO);
            return;
        }

        SOUND_API.play(gamer.getPlayer(), SoundType.SELECTED);
        gamer.sendMessageLocale("PREFIX_SET");

        String newPrefix = gamer.getPrefix().replaceAll("§[0-9a-e]", "§" + chatColor.getChar());
        if (newPrefix.equalsIgnoreCase(gamer.getPrefix())) {
            return;
        }

        gamer.setPrefix(newPrefix);
        BukkitUtil.runTaskAsync(() -> GlobalLoader.setPrefix(gamer.getPlayerID(), "§" + chatColor.getChar()));
        SCORE_BOARD_API.setPrefix(gamer.getPlayer(), newPrefix);
    }
}
