package net.lastcraft.packetlib.libraries.inventory;

import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.inventory.type.MultiInventory;
import net.lastcraft.api.inventory.type.ScrollInventory;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.packetlib.libraries.inventory.inventories.CraftDInventory;
import net.lastcraft.packetlib.libraries.inventory.inventories.CraftMultiInventory;
import net.lastcraft.packetlib.libraries.inventory.inventories.CraftScrollInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class InventoryAPIImpl implements InventoryAPI {

    private final GuiManagerListener listener;

    public InventoryAPIImpl(DartaAPI dartaAPI) {
        this.listener = new GuiManagerListener(dartaAPI);
    }

    @Override
    public DInventory createInventory(Player player, String name, int rows, InventoryAction inventoryAction) {
        return new CraftDInventory(player, name, rows, inventoryAction, listener);
    }

    @Override
    public DInventory createInventory(Player player, String name, int rows) {
        return createInventory(player, name, rows, null);
    }

    @Override
    public DInventory createInventory(String name, int rows) {
        return createInventory(null, name, rows);
    }

    @Override
    public DInventory createInventory(Player player, int rows, Language lang, String key, Object... objects) {
        return createInventory(player, lang.getMessage(key, objects), rows);
    }

    @Override
    public DInventory createInventory(int rows, Language lang, String key, Object... objects) {
        return createInventory(lang.getMessage(key, objects), rows);
    }

    @Override
    public MultiInventory createMultiInventory(Player player, String name, int rows) {
        return new CraftMultiInventory(player, name, rows);
    }

    @Override
    public MultiInventory createMultiInventory(String name, int rows) {
        return createMultiInventory(null, name, rows);
    }

    @Override
    public MultiInventory createMultiInventory(Player player, int rows, Language lang, String key, Object... objects) {
        return createMultiInventory(player, lang.getMessage(key, objects), rows);
    }

    @Override
    public MultiInventory createMultiInventory(int rows, Language lang, String key, Object... objects) {
        return createMultiInventory(lang.getMessage(key, objects), rows);
    }

    @Override
    public ScrollInventory createScrollInventory(Player player, String name, Language lang) {
        return new CraftScrollInventory(player, name, lang.getId());
    }

    @Override
    public ScrollInventory createScrollInventory(String name, Language lang) {
        return createScrollInventory(null, name, lang);
    }

    @Override
    public void pageButton(Language lang, int pagesCount, List<DInventory> pages, int slotDown, int slotUp) {
        for (int i = 0; i < pages.size(); i++) {
            int finalI = i;
            if (i == 0 && pagesCount > 1 && pages.size() > 1) {
                (pages.get(i)).setItem(slotUp, new DItem(ItemUtil.getBuilder(Material.SPECTRAL_ARROW)
                                .setName(lang.getMessage( "PAGE_ARROW1"))
                                .setLore(lang.getList( "PAGE_ARROW_LORE", (i + 2)))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI + 1).openInventory(player)));
            } else if (i > 0 && i < pagesCount - 1 && pages.size() > i + 1) {
                (pages.get(i)).setItem(slotDown, new DItem(ItemUtil.getBuilder(Material.SPECTRAL_ARROW)
                                .setName(lang.getMessage( "PAGE_ARROW2"))
                                .setLore(lang.getList("PAGE_ARROW_LORE", i))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI - 1).openInventory(player)));
                (pages.get(i)).setItem(slotUp, new DItem(ItemUtil.getBuilder(Material.SPECTRAL_ARROW)
                                .setName(lang.getMessage("PAGE_ARROW1"))
                                .setLore(lang.getList("PAGE_ARROW_LORE", (i + 2)))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI + 1).openInventory(player)));
            } else if (pages.size() > 1 && pagesCount > 1) {
                (pages.get(i)).setItem(slotDown, new DItem(ItemUtil.getBuilder(Material.SPECTRAL_ARROW)
                                .setName(lang.getMessage( "PAGE_ARROW2"))
                                .setLore(lang.getList("PAGE_ARROW_LORE", i))
                                .build(),
                        (player, clickType, slot) -> pages.get(finalI - 1).openInventory(player)));
            }
        }
    }

    @Override
    public void pageButton(Language lang, int pagesCount, MultiInventory inventory, int slotDown, int slotUp) {
        pageButton(lang, pagesCount, inventory.getInventories(), slotDown, slotUp);
    }
}
