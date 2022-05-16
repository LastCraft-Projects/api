package net.lastcraft.dartaapi.game.perk;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.action.InventoryAction;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.game.ItemsListener;
import net.lastcraft.dartaapi.guis.shop.ShopInventory;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class PerksGui extends ShopInventory {

    private Perk select = null;

    public PerksGui(Player player) {
        super(player);
        DInventory dInventory = LastCraft.getInventoryAPI().createInventory(player, "Умения", 5);
        dInventory.createInventoryAction(new InventoryAction() {
            @Override
            public void onOpen(Player player) {
                fillShopPages(0);
            }

            @Override
            public void onClose(Player player) {
                if (select == null) return;
                PerkSaveEvent event = new PerkSaveEvent(player, select);
                BukkitUtil.callEvent(event);
            }
        });
        shopPages.add(dInventory);
    }


    private void addPerk(int page, int position, Perk perk) {
        DInventory dInventory = shopPages.get(page);
        if (perk.has(player) || GAMER_MANAGER.getGamer(player).getGroup().getId() >= Group.JUNIOR.getId()) {
            dInventory.setItem(position, new DItem(
                    (select == null || select.getId() != perk.getId()) ? perk.getItem(player) : enablePerk(perk, false), (player, clickType, slot) -> {
                        DItem dItem = dInventory.getItems().get(slot);
                        if (select != null && select.getName().equals(perk.getName())) {
                            LastCraft.getSoundAPI().play(player, SoundType.DESTROY);
                            dItem.setItem(disablePerk(perk));
                        }
                        else {
                            Perk selectedPrev = select;
                            dItem.setItem(enablePerk(perk, true));
                            if (selectedPrev != null) {
                                addPerk(page, 10 + selectedPrev.getId() / 7 * 9 + selectedPrev.getId() % 7, selectedPrev);
                            }
                            LastCraft.getSoundAPI().play(player, SoundType.SELECTED);
                        }
                        dInventory.setItem(slot, dItem);
                    }));
        }
        else {
            dInventory.setItem(position, new DItem(perk.getWrongItem(player), (player, clickType, slot) -> {
                LastCraft.getSoundAPI().play(player, SoundType.NO);
                player.sendMessage(perk.getErrorMessage());
                player.closeInventory();
            }));
        }
    }

    @Override
    public Material getTrigger() {
        return ItemsListener.getPerk(player).getType();
    }

    @Override
    public void fillShopPages(int page) {
        for (int i = 0; i < Perk.perks.size(); ++i) {
            addPerk(0,10 + (i / 7) * 9 + i % 7, Perk.perks.get(i));
        }
    }

    private ItemStack enablePerk(Perk perkSelect, boolean msg) {
        select = perkSelect;
        if (msg)
            player.sendMessage(Perk.getPrefix() + "§fВы выбрали умение §e" + perkSelect.getName());

        ItemStack newItem = perkSelect.getItem(player);
        List<String> lore = newItem.getItemMeta().getLore();

        lore.addAll(Arrays.asList("", "§aУмение выбрано"));

        ItemUtil.setItemMeta(newItem, "§a" + newItem.getItemMeta().getDisplayName().substring(2), lore);
        ItemUtil.addEnchantment(newItem);

        return newItem;
    }

    private ItemStack disablePerk(Perk perkSelect) {
        select = null;
        player.sendMessage(Perk.getPrefix() + "§fВы отключили умение §c" + perkSelect.getName());
        return perkSelect.getItem(player);
    }

    public Perk getPerk() {
        return select;
    }

    public void setPerk(Perk perk) {
        if (perk != null) {
            select = perk;
        }
    }

    public void apply() {
        HandlerList.unregisterAll(this);
        if (select != null) select.onUse(this.player);
    }

}