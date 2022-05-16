package net.lastcraft.dartaapi.achievements.gui;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.action.ClickAction;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.inventory.type.MultiInventory;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.util.InventoryUtil;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.achievements.achievement.Achievement;
import net.lastcraft.dartaapi.achievements.achievement.AchievementPlayer;
import net.lastcraft.dartaapi.achievements.manager.AchievementManager;
import net.lastcraft.dartaapi.guis.CustomItems;
import net.lastcraft.api.inventory.DItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Deprecated //todo удалить
public class AchievementGui {

    protected static final InventoryAPI API = LastCraft.getInventoryAPI();
    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private final Player player;
    private MultiInventory inventory;
    private Language lang;

    public AchievementGui(Player player, String key) {
        this.player = player;

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;

        this.lang = gamer.getLanguage();
        this.inventory = API.createMultiInventory(player, lang.getMessage(key), 5);
    }

    public void setItems(AchievementManager achievementManager) {
        if (player == null || inventory == null || achievementManager == null)
            return;

        AchievementPlayer achievementPlayer = achievementManager.getPlayerManager().getAchievementPlayer(player);
        if (achievementPlayer == null)
            return;

        int slot = 10;
        int page = 0;
        for (Achievement achievement : achievementManager.getAchievements().values()) {
            ItemStack itemStack = ItemUtil.getBuilder(achievement.getItemStack(lang))
                    .setName("§a" + achievement.getName(lang))
                    .addLore("")
                    .addLore(lang.getMessage("ACHIEVEMENT_DONE"))
                    .build();

            if (!achievementPlayer.hasAchievement(achievement))
                itemStack = ItemUtil.getBuilder(Material.STAINED_GLASS_PANE)
                        .setDurability((short) 14)
                        .setName("§c" + achievement.getName(lang))
                        .setLore(lang.getList( achievement.getLoreKey()))
                        .addLore("")
                        .addLore(lang.getMessage("ACHIEVEMENT_PERCENT",
                                achievement.getPercent(achievementPlayer) + "%"))
                        .addLore("")
                        .addLore(lang.getMessage( "ACHIEVEMENT_NO_DONE"))
                        .build();
                //itemStack = ItemUtil.getBuilder(Material.STAINED_GLASS_PANE)
                //        .setDurability((short) 14)
                //        .setName("§c???")
                //        .setLore(Localization.getMessage(lang, "ACHIEVEMENT_NO_DONE"))
                //        .build();

            inventory.setItem(page, slot, new DItem(itemStack));

            slot++;

            if ((slot - 8) % 9 == 0)
                slot += 2;

            if (slot >= 35) {
                slot = 10;
                page++;
            }
        }

        int pagesCount = InventoryUtil.getPagesCount(achievementManager.getAchievements().size(), 21);
        API.pageButton(lang, pagesCount, inventory, 38, 42);
    }

    public void addBackItem(ClickAction clickAction) {
        if (inventory == null)
            return;

        inventory.setItem(40, new DItem(CustomItems.getBack(lang), clickAction));
    }

    public void open() {
        if (player == null || inventory == null)
            return;

        inventory.openInventory(player);
    }
}
