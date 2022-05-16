package net.lastcraft.dartaapi.guis.shop;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public abstract class ShopInventory implements Listener {

    protected static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    protected Player player;
    protected static InventoryAPI inventoryAPI;
    protected static SoundAPI soundAPI;

    public static Map<String, ShopInventory> shopPlayers = new ConcurrentHashMap<>();

    /*
    Страницы ивентаря. Начальная страница - 0.
    Поддерживает передвижение по страницам.
     */
    protected List<DInventory> shopPages = new ArrayList<>();

    public List<DInventory> getShopPages() {
        return shopPages;
    }

    public Player getPlayer() {
        return player;
    }

    protected abstract Material getTrigger(); //Какой предмет вызывает открытие инвенторя

    protected abstract void fillShopPages(int page); //начальное заполение страниц

    public ShopInventory(Player player) {
        this.player = player;
        if (getTrigger() != null) {
            shopPlayers.put(player.getName(), this);
        }
    }

    static {
        soundAPI = LastCraft.getSoundAPI();
        inventoryAPI = LastCraft.getInventoryAPI();
        new ShopListener();
    }

}
