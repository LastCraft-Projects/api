package net.lastcraft.dartaapi.game.spectator;

import net.lastcraft.api.ActionBarAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.PlayerKillEvent;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.GameState;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.inventory.DItem;
import net.lastcraft.api.inventory.InventoryAPI;
import net.lastcraft.api.inventory.type.DInventory;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.util.InventoryUtil;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.game.module.WaitModule;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketCamera;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpectatorMenu {
    private static final InventoryAPI INVENTORY_API = LastCraft.getInventoryAPI();
    private static final List<SpectatorMenu> MENUS = new ArrayList<>();
    private static final ActionBarAPI ACTION_BAR_API = LastCraft.getActionBarAPI();
    private static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    static final Map<String, String> CAMERA = new ConcurrentHashMap<>();
    private List<DInventory> pages = new ArrayList<>();
    private Language lang;

    public static void createMenu() {
        for (Language lang : Language.values()) {
            new SpectatorMenu(lang);
        }
        new Thread(() -> {
            while (GameState.getCurrent() == GameState.GAME) {
                try {
                    for (String spectatorName : CAMERA.values()) {
                        Player spectator = Bukkit.getPlayer(spectatorName);
                        if (spectator == null) continue;
                        BukkitGamer gamer = GAMER_MANAGER.getGamer(spectator);
                        if (gamer == null) return;
                        ACTION_BAR_API.sendBar(spectator, gamer.getLanguage().getMessage("SPECTATOR_CAMERA_ACTIONBAR"));
                    }
                    Thread.sleep(5 * 50);
                    update();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static void clearData() {
        MENUS.clear();
        CAMERA.clear();
    }

    private SpectatorMenu(Language lang) {
        this.lang = lang;
        int size = PlayerUtil.getAlivePlayers().size();
        int pagesCount = InventoryUtil.getPagesCount(size, 28);
        DInventory dInventory;
        for (int i = 0; i < pagesCount; ++i) {
            if (pagesCount > 1 && i != 0) {
                dInventory = INVENTORY_API.createInventory(lang.getMessage(
                        "SPECTATOR_MENU_NAME") + " | " + (i + 1), 6);
            } else {
                dInventory = INVENTORY_API.createInventory(lang.getMessage("SPECTATOR_MENU_NAME"), 6);
            }
            pages.add(dInventory);
        }
        MENUS.add(this);

        updateInventory();
    }

    private static String onPercent(double now, double max) {
        now = now * 100 / max;
        String color = "a";
        if (now <= 25) color = "c";
        if (25 < now && now < 85) color = "e";
        if (now >= 85) color = "a";
        return "§" + color + (int) now + "%";
    }

    private List<String> addLore(Player player) {
        List<String> lore = new ArrayList<>();
        TeamManager team = SelectionTeam.getSelectedTeams().get(player);

        lore.add(" ");
        if (PlayerKillEvent.getPlayerKiller(player) != null) {
            int kills = PlayerKillEvent.getPlayerKiller(player).getKills().size();
            lore.add(lang.getMessage( "SPECTATOR_KILLS_LORE", String.valueOf(kills)));
        } else {
            lore.add(lang.getMessage("SPECTATOR_KILLS_LORE", String.valueOf(0)));
        }

        if (GameSettings.teamMode && team != null) {
            lore.add(lang.getMessage("SPECTATOR_TEAM_LORE",
                    team.getChatColor() + (lang == Language.RUSSIAN ? team.getName() : team.getTeam())));
        }

        if (WaitModule.perkgui != null && WaitModule.perkgui.get(player) != null) {
            if (WaitModule.perkgui.get(player).getPerk() != null) {
                lore.add(lang.getMessage("SPECTATOR_PERK_LORE",
                        WaitModule.perkgui.get(player).getPerk().getName()));
            } else {
                lore.add(lang.getMessage("SPECTATOR_PERK_LORE",
                        lang.getMessage("SPECTATOR_NO_PERK_LORE")));
            }
        }

        lore.addAll(lang.getList("SPECTATOR_MAIN_LORE", onPercent(player.getHealth(),
                player.getMaxHealth()), onPercent(player.getFoodLevel(), 20)));
        return lore;
    }


    public void updateInventory() {
        int pagesCount = InventoryUtil.getPagesCount(PlayerUtil.getAlivePlayers().size(), 28);

        int slot = 10;
        int pageNum = 0;
        for (DInventory inventory : pages)
            inventory.clearInventory();

        INVENTORY_API.pageButton(lang, pagesCount, pages, 47, 51);

        for (Player alive : PlayerUtil.getAlivePlayers()) {
            BukkitGamer gamer = GAMER_MANAGER.getGamer(alive);
            if (gamer == null)
                continue;
            (pages.get(pageNum)).setItem(slot, new DItem(ItemUtil.setItemMeta(
                    gamer.getHead(),
                    "§r" + gamer.getPrefix() + alive.getName(),
                    addLore(alive), 1), (player, clickType, slot1) -> {
                player.teleport(gamer.getPlayer().getLocation());
                if (clickType.isRightClick()) {
                    BukkitUtil.runTaskLaterAsync(5L, () -> {
                        LastCraft.getTitlesAPI().sendTitle(player,
                                lang.getMessage( "SPECTATOR_CAMERA_TITLE"),
                                lang.getMessage("SPECTATOR_CAMERA_SUBTITLE", gamer.getChatName()));
                        Player gamerPlayer = gamer.getPlayer();
                        if (gamerPlayer != null) {
                            PacketCamera packetCamera = PACKET_CONTAINER.getCameraPacket(gamerPlayer);
                            packetCamera.sendPacket(player);
                        }
                        //Object entityPlayer = ReflectionUtils.getNMSPlayer(gamer.getOwner());
                        //PacketPlayOutCamera packet = newlocale PacketPlayOutCamera(entityPlayer);
                        //packet.sendPacket(achievement);
                    });
                    if (CAMERA.containsValue(player.getName())) {
                        for (String aliveName : CAMERA.keySet()) {
                            if (!CAMERA.get(aliveName).equals(player.getName())) continue;
                            CAMERA.remove(aliveName, player.getName());
                        }
                    }
                    player.getInventory().setHeldItemSlot(1);
                    PlayerUtil.getSpectators().forEach(spectator -> spectator.hidePlayer(player));
                    CAMERA.put(gamer.getPlayer().getName(), player.getName());
                }
                player.closeInventory();
            }));

            if (slot == 16) {
                slot = 19;
            } else if (slot == 25) {
                slot = 28;
            } else if (slot == 34) {
                slot = 37;
            } else if (slot == 43) {
                slot = 10;
                ++pageNum;
            } else {
                ++slot;
            }
        }
    }

    public static void openInventory(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        if (gamer == null)
            return;

        Language lang = gamer.getLanguage();
        for (SpectatorMenu spectatorMenu : MENUS) {
            if (spectatorMenu.lang == lang) {
                DInventory dInventory = spectatorMenu.pages.get(0);
                if (dInventory != null) {
                    dInventory.openInventory(player);
                }
            }
        }
    }

    public static void update() {
        for (SpectatorMenu spectatorMenu : MENUS) {
            spectatorMenu.updateInventory();
        }
    }
}
