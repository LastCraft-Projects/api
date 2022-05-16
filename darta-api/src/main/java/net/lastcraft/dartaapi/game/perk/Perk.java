package net.lastcraft.dartaapi.game.perk;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.util.Rarity;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public abstract class Perk {

    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    protected int id = 0, cost = 0;
    protected Rarity rarity = Rarity.COMMON;

    public static List<Perk> perks = new ArrayList<>();

    public static HashMap<String, Perk> perksNames = new HashMap<>();

    public Perk() {
        id = perks.size();
        perks.add(this);
        perksNames.put(getName(), this);
    }
    public Perk(Rarity rarity, int cost) {
        id = perks.size();
        perks.add(this);
        perksNames.put(getName(), this);
        this.rarity = rarity;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return cost;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public ItemStack getWrongItem(Player player) {
        ItemStack wrongItem = new ItemStack(Material.STAINED_GLASS_PANE,1 ,(short) 14);
        List<String> lore = getItem(player).getItemMeta().getLore();
        lore.addAll(Arrays.asList("", "§cУмение недоступно"));
        ItemUtil.setItemMeta(wrongItem, "§c" + getItem(player).getItemMeta().getDisplayName().substring(2), lore);
        return wrongItem;
    }

    public abstract boolean canAfford(Player player); //Может ли игрок взять данный перк

    public abstract ItemStack getItem(Player player); //Иконка перка

    public abstract ItemStack getItemPublic(); //Иконка перка

    public abstract void onUse(Player player); //Как используется

    public abstract boolean has(Player player);

    public abstract String getName(); //Название перка

    public abstract String getErrorMessage();

    public static String getPrefix() {
        return "§6Умения §8| ";
    }




}
