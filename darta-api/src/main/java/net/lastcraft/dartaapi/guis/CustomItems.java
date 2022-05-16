package net.lastcraft.dartaapi.guis;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.experimental.UtilityClass;
import net.lastcraft.api.util.Head;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.locale.Language;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class CustomItems {

    private static final TIntObjectMap<ItemStack> BACK_ITEMS = new TIntObjectHashMap<>();
    private static final TIntObjectMap<ItemStack> BACK_ITEMS2 = new TIntObjectHashMap<>();
    private static final TIntObjectMap<ItemStack> ENABLE_ITEM = new TIntObjectHashMap<>();
    private static final TIntObjectMap<ItemStack> DISABLE_ITEM = new TIntObjectHashMap<>();

    public ItemStack getBack(Language lang) {
        ItemStack itemStack = BACK_ITEMS.get(lang.getId());
        if (itemStack != null)
            return itemStack.clone();

        return BACK_ITEMS.get(Language.getDefault().getId()).clone();
    }

    public ItemStack getBack2(Language lang) {
        ItemStack itemStack = BACK_ITEMS2.get(lang.getId());
        if (itemStack != null)
            return itemStack.clone();

        return BACK_ITEMS2.get(Language.getDefault().getId()).clone();
    }

    public ItemStack getEnable(Language lang) {
        ItemStack itemStack = ENABLE_ITEM.get(lang.getId());
        if (itemStack != null)
            return itemStack.clone();

        return ENABLE_ITEM.get(Language.getDefault().getId()).clone();
    }

    public  ItemStack getDisable(Language lang) {
        ItemStack itemStack = DISABLE_ITEM.get(lang.getId());
        if (itemStack != null)
            return itemStack.clone();

        return DISABLE_ITEM.get(Language.getDefault().getId()).clone();
    }

    private void init() {
        for (Language language : Language.values()) {
            int lang = language.getId();
            BACK_ITEMS.put(lang, ItemUtil.getBuilder(Head.BACK)
                    .setName(language.getMessage("PROFILE_BACK_ITEM_NAME"))
                    .setLore(language.getList("PROFILE_BACK_ITEM_LORE"))
                    .build());
            BACK_ITEMS2.put(lang, ItemUtil.getBuilder(Head.BACK)
                    .setName(language.getMessage("PROFILE_BACK_ITEM_NAME"))
                    .setLore(language.getList( "PROFILE_BACK_ITEM_LORE2"))
                    .build());
            ENABLE_ITEM.put(lang, ItemUtil.getBuilder(Material.INK_SACK)
                    .setDurability((short) 10)
                    .setName("§a" + language.getMessage( "ENABLE"))
                    .setLore(language.getList( "ITEMS_LOBBY_ENABLE_LORE"))
                    .build());
            DISABLE_ITEM.put(lang, ItemUtil.getBuilder(Material.INK_SACK)
                    .setDurability((short) 8)
                    .setName("§c" + language.getMessage("DISABLE"))
                    .setLore(language.getList("ITEMS_LOBBY_DISABLE_LORE"))
                    .build());
        }
    }

    static {
        init();
    }
}
