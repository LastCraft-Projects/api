package net.lastcraft.dartaapi.donatemenu.guis;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.inventory.type.MultiInventory;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.api.util.ItemUtil;
import net.lastcraft.base.SoundType;
import net.lastcraft.dartaapi.donatemenu.DonateMenuData;
import net.lastcraft.dartaapi.guis.CustomItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DonateMenuGui {

    protected static final SoundAPI SOUND_API = LastCraft.getSoundAPI();
    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    protected static final InventoryAPI INVENTORY_API = LastCraft.getInventoryAPI();

    protected static final ItemStack NO_PERMS = ItemUtil.getBuilder(Material.STAINED_GLASS_PANE)
            .setDurability((short) 14)
            .build();

    protected final Player player;
    protected final DonateMenuData donateMenuData;
    protected final MultiInventory inventory;

    DonateMenuGui(Player player, DonateMenuData donateMenuData, String name) {
        this.player = player;
        this.donateMenuData = donateMenuData;
        this.inventory = INVENTORY_API.createMultiInventory(player, name, 5);
    }

    protected final void setBack(DonateMenuGui gui) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;
        inventory.setItem(40, new DItem(CustomItems.getBack2(gamer.getLanguage()),
                (clicker, clickType, slot) -> {
                    if (gui == null) {
                        return;
                    }
                    SOUND_API.play(player, SoundType.PICKUP);
                    gui.open();
        }));
    }

    protected abstract void setItems(BukkitGamer gamer);

    public final void open() {
        if (inventory == null || player == null) {
            return;
        }

        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null) {
            return;
        }

        setItems(gamer);
        inventory.openInventory(player);
    }
}
