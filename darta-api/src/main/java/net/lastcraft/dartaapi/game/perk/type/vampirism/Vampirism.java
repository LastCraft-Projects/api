package net.lastcraft.dartaapi.game.perk.type.vampirism;

import net.lastcraft.api.util.Rarity;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.game.perk.Perk;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Vampirism extends Perk {

    private final String name = "Вампиризм";

    public Vampirism() {
        super();
    }
    public Vampirism(Rarity rarity, int cost) {
        super(rarity, cost);
    }

    @Override
    public boolean canAfford(Player player) {
        return GAMER_MANAGER.getGamer(player).getGroup().getLevel() >= 2;
    }

    @Override
    public ItemStack getItem(Player player) {
        int level = GAMER_MANAGER.getGamer(player).getGroup().getLevel();
        return ItemUtil.createItemStack(Material.SKULL_ITEM, (byte)1, "§e" + name, Arrays.asList(
                "§7Восстанавливает ваше здоровье",
                "§7при убийстве другого игрока на:",
                " §8▪§7 1 " + (level == 2 ? "§a" : "§c")+ "❤ §7для §f[§bDiamond§f]",
                " §8▪§7 2 " + (level == 3 ? "§a" : "§c")+ "❤ §7для §f[§aEmerald§f]",
                " §8▪§7 3 " + (level >= 4 ? "§a" : "§c")+ "❤ §7для §f[§cMagma§f] §7и выше"
        ));
    }

    @Override
    public ItemStack getItemPublic() {
        return new ItemStack(Material.SKULL, 1, (byte)1);
    }

    @Override
    public void onUse(Player player) {
        byte count;
        switch (GAMER_MANAGER.getGamer(player).getGroup().getLevel()) {
            case 2:
                count = 2;
                break;
            case 3:
                count = 4;
                break;
            default:
                count = 6;
        }

        new VampListener(player, count);
    }

    @Override
    public boolean has(Player player) {
        return canAfford(player);
    }

    public String getName() { return name; }

    @Override
    public String getErrorMessage() {
        return (Perk.getPrefix() + "§cУ вас нет прав на это умение, купите §r"+ Group.getGroupByLevel(2).getPrefix() + "§cили выше");
    }
}