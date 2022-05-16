package net.lastcraft.dartaapi.game.team;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.sound.SoundAPI;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.DIterator;
import net.lastcraft.dartaapi.game.module.WaitModule;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SelectionTeam {
    private static final SoundAPI SOUND_API = LastCraft.getSoundAPI();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final InventoryAPI INVENTORY_API = LastCraft.getInventoryAPI();

    private DInventory dInventory;
    private Language lang;
    private Player player;

    private static Map<Player, TeamManager> selectedTeams = new ConcurrentHashMap<>();
    private static HashMap<Integer, TeamManager> slots = new HashMap<>();

    public static Map<Player, TeamManager> getSelectedTeams(){
        return selectedTeams;
    }

    public static void resetSelectedTeams(Player player){
        selectedTeams.remove(player);
        updateInventoryAll();
    }

    public SelectionTeam(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;

        lang = gamer.getLanguage();
        dInventory = INVENTORY_API.createInventory(player, lang.getMessage("SELECTOR_TEAM_NAME_INV", player.getName()), TeamManager.getSlotsTeams()[0] / 9);

        this.player = player;
        createInventory();
    }

    private void createInventory() {
        int[] slotsTeams = TeamManager.getSlotsTeams();
        DIterator<TeamManager> teams = new DIterator<>(TeamManager.getTeams().values());
        for (int i = 1; i < slotsTeams.length; i++) {
            TeamManager team = teams.getNext();
            dInventory.setItem(slotsTeams[i], new DItem(ItemUtil.createItemStack(
                    Material.STAINED_GLASS,
                    team.getSubID(),
                    team.getChatColor()
                            + (lang == Language.RUSSIAN ? team.getName() : team.getTeam())
                            + " [0/"
                            + GameSettings.playersInTeam
                            + "]",
                    Collections.singletonList(lang.getMessage("SELECTOR_TEAM_NO_PLAYERS"))),
                    (player, clickType, slot) -> {
                if (getPlayersInTeam(team) < GameSettings.playersInTeam) {
                    if (selectedTeams.get(player) != team) {
                        selectedTeams.put(player, team);
                        SOUND_API.play(player, SoundType.POP);
                        updateInventoryAll();
                    } else {
                        SOUND_API.play(player, SoundType.NO);
                    }
                } else {
                    SOUND_API.play(player, SoundType.NO);
                }
            }));
            slots.put(slotsTeams[i], team);
        }
        if (Bukkit.getOnlinePlayers().size() > 1) {
            updateInventory();
        }
    }

    public DInventory getdInventory() {
        return dInventory;
    }

    public static void open(Player player) {
        SelectionTeam selectionTeam = WaitModule.getSelectionTeam().get(player.getName());
        if (selectionTeam != null) {
            DInventory dInventory = selectionTeam.getdInventory();
            dInventory.openInventory(player);
        }
    }

    public static void updateInventoryAll() {
        for (SelectionTeam selectionTeam : WaitModule.getSelectionTeam().values())
            selectionTeam.updateInventory();

    }

    private void updateInventory() {
        for (Map.Entry<Integer, TeamManager> itemSlot : slots.entrySet()) {
            TeamManager team = itemSlot.getValue();
            if (!GAMER_MANAGER.containsGamer(player)) return;
            int playersInTeam = getPlayersInTeam(team);
            if (selectedTeams.get(player) == team) {
                ItemStack itemStack = GAMER_MANAGER.getGamer(player).getHead();
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(lang.getMessage( "SELECTOR_TEAM_PLAYERS"));
                lore.addAll(selectedTeams.entrySet()
                        .stream()
                        .filter(selectedTeam -> selectedTeam.getValue() == team)
                        .map(selectedTeam -> " §8▪ §r" + selectedTeam.getKey().getDisplayName())
                        .collect(Collectors.toList()));
                if (!selectedTeams.containsValue(team)) {
                    lore.clear();
                    lore.add(lang.getMessage("SELECTOR_TEAM_NO_PLAYERS"));
                }
                if (playersInTeam == 0)
                    playersInTeam = 1;

                itemStack.setAmount(playersInTeam);
                itemMeta.setDisplayName(team.getChatColor() + (lang == Language.RUSSIAN ? team.getName() : team.getTeam()) + " [" +
                        getPlayersInTeam(team) + "/" + GameSettings.playersInTeam + "]");
                itemMeta.setLore(lore);

                itemStack.setItemMeta(itemMeta);

                int slot = itemSlot.getKey();
                DItem item = dInventory.getItems().get(slot);
                item.setItem(itemStack);
                dInventory.setItem(slot, item);
            } else {
                ItemStack itemStack = ItemUtil.createItemStack(Material.STAINED_GLASS, team.getSubID(),
                        team.getChatColor() + (lang == Language.RUSSIAN ? team.getName() : team.getTeam()),
                        Collections.singletonList(lang.getMessage( "SELECTOR_TEAM_PLAYERS")));
                ItemMeta itemMeta = itemStack.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.addAll(itemStack.getItemMeta().getLore());
                lore.addAll(selectedTeams.entrySet()
                        .stream()
                        .filter(selectedTeam -> selectedTeam.getValue() == team)
                        .map(selectedTeam -> " §8▪ §r" + selectedTeam.getKey().getDisplayName())
                        .collect(Collectors.toList()));
                if (!selectedTeams.containsValue(team)) {
                    lore.clear();
                    lore.add(lang.getMessage( "SELECTOR_TEAM_NO_PLAYERS"));
                }
                if (playersInTeam == 0) {
                    playersInTeam = 1;
                }
                itemStack.setAmount(playersInTeam);
                itemMeta.setDisplayName(itemMeta.getDisplayName() + " [" + getPlayersInTeam(team) + "/" +
                        GameSettings.playersInTeam + "]");
                itemMeta.setLore(lore);

                itemStack.setItemMeta(itemMeta);

                int slot = itemSlot.getKey();
                DItem item = dInventory.getItems().get(slot);
                item.setItem(itemStack);
                dInventory.setItem(slot, item);
            }
        }
    }

    public static int getPlayersInTeam(TeamManager team){
        int count = 0;
        for (Map.Entry<Player, TeamManager> selectedTeam : selectedTeams.entrySet()){
            if (selectedTeam.getValue() == team){
                count++;
            }
        }
        return count;
    }

    public static List<Player> getPlayersByTeam(TeamManager team){
        List<Player> list = new ArrayList<>();
        for (Map.Entry<Player, TeamManager> selectedTeam : selectedTeams.entrySet()){
            if (selectedTeam.getValue() == team){
                list.add(selectedTeam.getKey());
            }
        }
        return list;
    }
}
