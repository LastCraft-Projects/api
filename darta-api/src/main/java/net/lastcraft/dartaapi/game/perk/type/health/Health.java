package net.lastcraft.dartaapi.game.perk.type.health;

import net.lastcraft.api.util.Rarity;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.game.perk.Perk;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Health extends Perk {

    private final String name = "Здоровье";

    public Health() { super(); }

    public Health(Rarity rarity, int cost) {
        super(rarity, cost);
    }

    @Override
    public boolean canAfford(Player player) {
        return GAMER_MANAGER.getGamer(player).isDiamond();
    }

    public ItemStack getItem(Player player) {
        int level = GAMER_MANAGER.getGamer(player).getGroup().getLevel();
        return new ItemStack(ItemUtil.createItemStack(Material.POTION, (byte) 373, "§e" + name, Arrays.asList(
                "§7Увеличивает максимальное",
                "§7здоровье игрока на:",
                " §8▪§7 1 " + (level == 2 ? "§a" : "§c") + "❤ §7для §f[§bDiamond§f]",
                " §8▪§7 1.5 " + (level == 3 ? "§a" : "§c") + "❤ §7для §f[§aEmerald§f]",
                " §8▪§7 2 " + (level >= 4 ? "§a" : "§c") + "❤ §7для §f[§cMagma§f] §7и выше"
        )));
    }

    @Override
    public ItemStack getItemPublic() {
        return new ItemStack(Material.POTION, 1, (byte)373);
    }

    @Override
    public void onUse(Player player) {
        int level = GAMER_MANAGER.getGamer(player).getGroup().getLevel();
        if (level > 4) level = 4;
        player.setMaxHealth(20 + level);
    }

    @Override
    public boolean has(Player player) {
        return canAfford(player);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getErrorMessage() {
        return Perk.getPrefix() + "§cУ вас нет прав на это умение, купите §r"+ Group.getGroupByLevel(2).getPrefix() + "§cили выше";
    }
}
