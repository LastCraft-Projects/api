package net.lastcraft.dartaapi.guis.shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Deprecated
public interface ItemShop  {

    ItemShop getByIcon(ItemStack icon);

    ItemStack getIcon();

    void choose(Player player);

    void giveToPlayer(Player player, boolean buy);

    boolean canBuy(Player player);

    boolean have(Player Player);
}
