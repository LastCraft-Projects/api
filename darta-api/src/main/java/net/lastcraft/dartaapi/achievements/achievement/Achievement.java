package net.lastcraft.dartaapi.achievements.achievement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.locale.Language;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public abstract class Achievement {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    @Getter
    private final int id;
    private final ItemStack itemStack;
    private final String nameKey;
    private final String loreKey;

    public ItemStack getItem() {
        return itemStack.clone();
    }

    public ItemStack getItemStack(Language lang, Object... objects) {
        return ItemUtil.getBuilder(itemStack.clone())
                .setName(lang.getMessage(nameKey))
                .setLore(lang.getList(loreKey, objects))
                .build();
    }

    public ItemStack getItemStack(Player player, Object... objects) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return getItemStack(Language.getDefault());

        return getItemStack(gamer.getLanguage(), objects);
    }

    public String getName(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return getName(Language.getDefault());

        return getName(gamer.getLanguage());
    }

    public String getName(Language lang) {
        return lang.getMessage(nameKey);
    }

    public final String getLoreKey() {
        return loreKey;
    }

    /**
     * узнать сколько % ачивки выполнено
     * @return - сколько %
     */
    public abstract int getPercent(AchievementPlayer achievementPlayer);

    /**
     * кол-во очков за выполнение ачивки
     * @return - кол-во очков
     */
    public abstract int getPoints();

    /**
     * вызывается когда ачивка была выполнен
     * @param player - кто выполнил
     */
    protected abstract void complete(BukkitGamer gamer);
}