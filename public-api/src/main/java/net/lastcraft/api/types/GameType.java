package net.lastcraft.api.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.util.Head;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Kambet on 30.01.2018
 */
@AllArgsConstructor
@Getter
public enum GameType {

    SW("SkyWars", "swlobby", new ItemStack(Material.ENDER_PEARL)),
    BW("BedWars", "bwlobby", new ItemStack(Material.BED)),
    KW("KitWars", "kwlobby", Head.BATMAN.getHead()),
    SG("SurvivalGames", "sglobby", new ItemStack(Material.CHEST)),
    LW("LuckyWars", "lwlobby", Head.LUCKYWARS.getHead()),
    EW("EggWars", "ewlobby", new ItemStack(Material.DRAGON_EGG)),
    PR("ParkourRacers", "prlobby", new ItemStack(Material.LADDER)),
    ARCADE("ArcadeGames", "arlobby", new ItemStack(Material.BLAZE_POWDER)),

    UNKNOWN("Hub", "hub", Head.MAINLOBBY.getHead());

    public static GameType current = UNKNOWN;

    private final String name;
    private final String lobbyChannel;
    private final ItemStack itemStack;

    public ItemStack getItemStack() {
        return itemStack.clone();
    }
}
