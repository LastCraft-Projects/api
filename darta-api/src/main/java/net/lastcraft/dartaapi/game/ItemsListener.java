package net.lastcraft.dartaapi.game;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.usableitem.ClickType;
import net.lastcraft.api.usableitem.UsableAPI;
import net.lastcraft.api.usableitem.UsableItem;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.spectator.SPlayer;
import net.lastcraft.dartaapi.game.spectator.SpectatorMenu;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItemsListener {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private static Map<Integer, UsableItem> selectionTeam;
    private static Map<Integer, UsableItem> kits;
    private static Map<Integer, UsableItem> teleporter;
    private static Map<Integer, UsableItem> perks;
    private static Map<Integer, UsableItem> hub;
    private static Map<Integer, UsableItem> startAgain;
    private static Map<Integer, UsableItem> spectatorSettings;

    public ItemsListener() {
        UsableAPI usableAPI = LastCraft.getUsableAPI();
        selectionTeam = new ConcurrentHashMap<>();
        kits = new ConcurrentHashMap<>();
        teleporter = new ConcurrentHashMap<>();
        perks = new ConcurrentHashMap<>();
        hub = new ConcurrentHashMap<>();
        startAgain = new ConcurrentHashMap<>();
        spectatorSettings = new ConcurrentHashMap<>();

        for (Language language : Language.values()) {
            int locale = language.getId();
            selectionTeam.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.ANVIL,
                    language.getMessage("TEAM_ITEM_NAME"),
                    language.getList( "TEAM_ITEM_LORE")), (player, clickType, block) -> {
                if (clickType != ClickType.RIGHT) return;
                SelectionTeam.open(player);
            }));
            kits.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.IRON_SWORD,
                    language.getMessage("KIT_ITEM_NAME"),
                    language.getList( "KIT_ITEM_LORE")), (player, clickType, block) -> {
                //пусто
            }));
            perks.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.CHEST,
                    language.getMessage("PERK_ITEM_NAME"),
                    language.getList("PERK_ITEM_LORE")), (player, clickType, block) -> {
                //пусто
            }));
            teleporter.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.COMPASS,
                    language.getMessage("SPECTATOR_MENU_ITEM_NAME"),
                    language.getList("SPECTATOR_MENU_ITEM_LORE")), (player, clickType, block) -> {
                if (clickType != ClickType.RIGHT) return;
                SpectatorMenu.openInventory(player);
            }));
            hub.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.SLIME_BALL,
                    language.getMessage( "HUB_ITEM_NAME"),
                    language.getList("HUB_ITEM_LORE")), (player, clickType, block) -> {
                if (clickType != ClickType.RIGHT) return;
                PlayerUtil.redirectToHub(player);
            }));
            startAgain.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.PAPER,
                    language.getMessage("PLAYAGAIN_ITEM_NAME"),
                    language.getList( "PLAYAGAIN_ITEM_LORE")), (player, clickType, block) -> {
                //пусто
            }));
            spectatorSettings.put(locale, usableAPI.createUsableItem(ItemUtil.createItemStack(Material.REDSTONE_COMPARATOR,
                    language.getMessage( "SPECTATOR_SETTINGS_ITEM_NAME"),
                    language.getList( "SPECTATOR_SETTINGS_ITEM_LORE")), (player, clickType, block) -> {
                if (clickType != ClickType.RIGHT) return;
                SPlayer.getSPlayer(player).openInventory();
            }));
        }
    }

    public static ItemStack getCompass(){
        ItemStack itemStack = new ItemStack(Material.COMPASS);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§aЛокатор");
        itemMeta.setLore(Arrays.asList(
                "§7Нажмите ПКМ, чтобы переключить",
                "§7указатель на другого игрока"
        ));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack getPerk(Player player){
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(perks, lang);
    }

    public static ItemStack getKits(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(kits, lang);
    }

    public static ItemStack getSelectionTeam(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(selectionTeam, lang);
    }

    public static ItemStack getTeleporter(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(teleporter, lang);
    }

    public static ItemStack getHub(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(hub, lang);
    }

    public static ItemStack getStartAgain(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(startAgain, lang);
    }

    public static ItemStack getSpectatorSettings(Player player) {
        if (!player.isOnline()) return null;
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return null;
        int lang = gamer.getLanguage().getId();
        return get(spectatorSettings, lang);
    }

    private static ItemStack get(Map<Integer, UsableItem> map, int lang) {
        UsableItem usableItem = map.get(lang);
        if (usableItem == null) {
           usableItem = map.get(Language.getDefault().getId());
        }

        return usableItem.getItemStack();
    }
}
