package net.lastcraft.dartaapi.game.perk.type.bowbreakblock;

import net.lastcraft.api.util.Rarity;
import net.lastcraft.dartaapi.game.perk.Perk;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BowBreakBlock extends Perk {

    private final String name = "Лук-Разрушитель";

    public BowBreakBlock() {
        super();
    }
    public BowBreakBlock(Rarity rarity, int cost) {
        super(rarity, cost);
    }

    @Override
    public boolean canAfford(Player player) {
        return true;
    }

    @Override
    public ItemStack getItem(Player player) {
        return ItemUtil.createItemStack(Material.BOW, "§e" + name, Arrays.asList(
                "§7Уничтожение блоков при выстреле",
                "§7по ним с любого лука"
        ));
    }

    @Override
    public ItemStack getItemPublic() {
        return new ItemStack(Material.BOW);
    }

    @Override
    public void onUse(Player player) {
        new ArrowBlockBreakListener(player);
    }

    @Override
    public boolean has(Player player) {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getErrorMessage() {
        return "Обратитесь к администрации проекта";
    }
}
