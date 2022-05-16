package net.lastcraft.dartaapi.game.depend;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.depend.CraftVector;
import net.lastcraft.api.effect.ParticleAPI;
import net.lastcraft.api.entity.EntityEquip;
import net.lastcraft.api.entity.EquipType;
import net.lastcraft.api.entity.stand.CustomStand;
import net.lastcraft.api.event.game.EndGameEvent;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.PlayerTag;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.base.gamer.constans.KeyType;
import net.lastcraft.base.gamer.constans.PurchaseType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.dartaapi.boards.EndBoard;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.stats.Stats;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.bukkit.LocationUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.core.StringUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class EndLobby extends DListener {

    private EndGameEvent endGameEvent;
    private static final ParticleAPI PARTICLE_API = LastCraft.getParticleAPI();
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();
    private static final ScoreBoardAPI SCORE_BOARD_API = LastCraft.getScoreBoardAPI();

    public EndLobby(EndGameEvent endGameEvent) {
        this.endGameEvent = endGameEvent;
        this.endLobby();
    }

    private void endLobby() {
        new BukkitRunnable() {
            int a = 1;
            @Override
            public void run() {
                for (Player player : endGameEvent.getWinners()) {
                    if (player.isOnline()) {
                        PARTICLE_API.shootRandomFirework(player);
                    }
                }
                if (++a > 20) {
                    cancel();
                }
            }
        }.runTaskTimer(DartaAPI.getInstance(), 20L, 20L);

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!GAMER_MANAGER.containsGamer(player)) {
                continue;
            }
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer == null) {
                continue;
            }
            Language lang = gamer.getLanguage();

            PlayerTag playerTag = SCORE_BOARD_API.createTag(SCORE_BOARD_API.getPriorityScoreboardTag(
                    gamer.getGroup()) + gamer.getName());
            playerTag.setPrefix(gamer.getPrefix());
            playerTag.addPlayerToTeam(player);
            playerTag.sendToAll();

            if (GameSettings.teamMode && endGameEvent.getTeamWin() != null) {
                EndBoard.createBoard(gamer, lang.getMessage( "WIN_TEAM_BOARD") + ":", endGameEvent.getTeamWin());
            } else {
                if (!endGameEvent.getWinners().isEmpty() && endGameEvent.getWinners().get(0) != null) {
                    EndBoard.createBoard(gamer,lang.getMessage("WIN_PLAYER_BOARD") + ":", endGameEvent.getWinners().get(0).getDisplayName());
                }
            }
        }

        Location locationSpawn = LocationUtil.stringToLocation("endlobby;1.5;68.0;-9.5;-2.7000182;-0.29683468", true);
        Location locationHolo = LocationUtil.stringToLocation("endlobby;1.5;70.0;-4.5", false);
        Location locationTopHolo = LocationUtil.stringToLocation("endlobby;1.5;68.0;8.5", false);
        Location locPos1 = LocationUtil.stringToLocation("endlobby;1.5;67.0;11.5;-0.0;-180.0", true);
        Location locPos2 = LocationUtil.stringToLocation("endlobby;4.5;67.0;9.5;-0.0;-180.0", true);
        Location locPos3 = LocationUtil.stringToLocation("endlobby;-1.5;67.0;9.5;-0.0;-180.0", true);

        Bukkit.broadcastMessage("§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(StringUtil.stringToCenter(endGameEvent.getWinMsg()));

        Stats st = GameManager.getInstance().getStats();
        Player[] killers = new Player[3];
        if (st.getStatsPlayers().size() == 2) killers = new Player[2];
        int i = 0;
        for (Player killer : st.getPlayersTop(endGameEvent.getTopValue()).keySet()) {
            if (i == 3) break;
            killers[i] = killer;
            i++;
        }

        Hologram hologram = LastCraft.getHologramAPI().createHologram(locationTopHolo.clone().subtract(0, 1.0, 0));
        hologram.addTextLine(endGameEvent.getHoloTop());
        hologram.setPublic(true);

        for (int a = 1; a <= killers.length; a++) {
            Location location = locPos1;
            if (a == 2) location = locPos2;
            if (a == 3) location = locPos3;
            BukkitGamer gamer = GAMER_MANAGER.getGamer(killers[a - 1]);

            if (gamer != null) {
                String name = gamer.getPrefix() + killers[a - 1].getName();
                int kills = st.getPlayerStats(killers[a - 1], endGameEvent.getTopValue());
                String killsString = StringUtil.getCorrectWord(kills, endGameEvent.getTopValueSuffix().split(";")[0], endGameEvent.getTopValueSuffix().split(";")[1], endGameEvent.getTopValueSuffix().split(";")[2], "");

                CustomStand stand = LastCraft.getEntityAPI().createStand(location.clone());
                stand.setArms(true);
                stand.setSmall(true);
                stand.setBasePlate(false);
                stand.setPublic(true);
                stand.getEntityEquip().setItem(EquipType.HEAD, gamer.getHead());
                setEquipment(stand, a);

                Hologram hologramStand = LastCraft.getHologramAPI().createHologram(location.clone().add(0.0D, 1.1D, 0.0D));
                hologramStand.addTextLine("§e" + a + " место");
                hologramStand.addTextLine(name);
                hologramStand.addTextLine(kills + " " + killsString);
                hologramStand.setPublic(true);
            }
        }

        BukkitUtil.runTaskAsync(() -> {
            //оффлайн игрокам(тиммейтам) выдаю их призы
            if (GameSettings.teamMode) {
                for (Player player : endGameEvent.getWinners()) {
                    if (player.isOnline()) {
                        continue;
                    }
                    BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
                    gamer.changeKeys(getKeyType(gamer), 1);
                    int money = (int) (gamer.getMoneyLocal() * gamer.getMultiple());
                    if (money >= 1) gamer.changeMoney(PurchaseType.MYSTERY_DUST, money);
                    int exp = gamer.getExpLocal();
                    if (exp >= 1) gamer.addExp(exp);
                }
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                BukkitUtil.runTask(() -> player.teleport(locationSpawn));
                if (!GAMER_MANAGER.containsGamer(player))
                    continue;
                BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
                Language lang = gamer.getLanguage();
                List<String> holoText = new ArrayList<>();
                holoText.add("§e" + lang.getMessage( "HOLO_REWARD_STATS"));
                holoText.add("§f" + lang.getMessage("HOLO_REWARD_MULTIPLAY") + ": §ex" + gamer.getMultiple());
                for (String patch : endGameEvent.getHoloInfo()) {
                    String stats = endGameEvent.getHoloPlayerInfo().get(player);
                    if (stats == null) continue;
                    for (String getStats : stats.split(";")) {
                        patch = patch.replaceAll(getStats.split(":")[0], getStats.split(":")[1]);
                    }
                    holoText.add(patch);
                }
                int money = (int) (gamer.getMoneyLocal() * gamer.getMultiple());
                holoText.add(lang.getMessage( "HOLO_REWARD_MONEY") + ": §6" + money);
                createHolo(player, locationHolo, holoText);

                player.sendMessage("");
                player.sendMessage(StringUtil.stringToCenter("§e" + lang.getMessage("YOU_REWARD_MSG") + ":"));
                player.sendMessage(StringUtil.stringToCenter("§6" + money + " " + StringUtil.getCorrectWord(money, "MONEY_2", lang.getId())));

                int exp = gamer.getExpLocal();
                if (endGameEvent.getWinners().contains(player)) {
                    KeyType keyType = getKeyType(gamer);

                    if (keyType != null) {
                        gamer.changeKeys(keyType, 1);
                        player.sendMessage(StringUtil.stringToCenter("§d1 " + keyType.getName(lang).substring(2)));
                    }
                }
                player.sendMessage(StringUtil.stringToCenter("§a" + exp + " XP"));

                if (money >= 1) {
                    gamer.changeMoney(PurchaseType.MYSTERY_DUST, money);
                }
                if (exp >= 1) {
                    gamer.addExp(exp);
                }
            }

            Bukkit.broadcastMessage("");
            Bukkit.broadcastMessage("§8▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀▀");
        });
    }

    private void createHolo(Player player, Location location, List<String> text) {
        Hologram holo = LastCraft.getHologramAPI().createHologram(location.clone().subtract(0, 0.4, 0));
        holo.addTextLine(text);
        holo.showTo(player);
    }

    private void setEquipment(CustomStand armorStand, int top) {
        Color color;
        ItemStack itemInHand;
        switch(top) {
            case 1:
                color = Color.fromRGB(23, 164, 79);
                itemInHand = new ItemStack(Material.EMERALD);
                armorStand.setLeftArmPose(new CraftVector(-20.0F, 0.0F, -120.0F));
                armorStand.setRightArmPose(new CraftVector(-40.0F, 50.0F, 90.0F));
                armorStand.setRightLegPose(new CraftVector(-10.0F, 70.0F, 40.0F));
                armorStand.setLeftLegPose(new CraftVector(-10.0F, -60.0F, -40.0F));
                armorStand.setHeadPose(new CraftVector(15.0F, 0.0F, 0.0F));
                break;
            case 2:
                color = Color.fromRGB(46, 210, 185);
                itemInHand = new ItemStack(Material.DIAMOND);
                armorStand.setLeftArmPose(new CraftVector(-20.0F, 0.0F, -140.0F));
                armorStand.setRightArmPose(new CraftVector(-50.0F, 20.0F, 10.0F));
                armorStand.setRightLegPose(new CraftVector(-5.0F, -10.0F, 10.0F));
                armorStand.setLeftLegPose(new CraftVector(-10.0F, -10.0F, -6.0F));
                armorStand.setHeadPose(new CraftVector(5.0F, 0.0F, 5.0F));
                break;
            case 3:
                color = Color.fromRGB(179, 132, 16);
                itemInHand = new ItemStack(Material.GOLD_INGOT);
                armorStand.setLeftArmPose(new CraftVector(50.0F, 15.0F, -7.0F));
                armorStand.setRightArmPose(new CraftVector(-50.0F, 10.0F, 5.0F));
                armorStand.setRightLegPose(new CraftVector(-20.0F, 0.0F, 5.0F));
                armorStand.setLeftLegPose(new CraftVector(20.0F, 0.0F, -5.0F));
                armorStand.setHeadPose(new CraftVector(0.0F, 0.0F, 2.0F));
                break;
            default:
                color = Color.fromRGB(0, 0, 0);
                itemInHand = new ItemStack(Material.BARRIER);
        }

        EntityEquip entityEquip = armorStand.getEntityEquip();
        entityEquip.setItem(EquipType.CHEST, ItemUtil.getColorLeatherArmor(Material.LEATHER_CHESTPLATE, color));
        entityEquip.setItem(EquipType.LEGS, ItemUtil.getColorLeatherArmor(Material.LEATHER_LEGGINGS, color));
        entityEquip.setItem(EquipType.FEET, ItemUtil.getColorLeatherArmor(Material.LEATHER_BOOTS, color));
        entityEquip.setItem(EquipType.MAINHAND, itemInHand);
    }

    private KeyType getKeyType(BukkitGamer gamer) {
        KeyType keyType = null;
        try {
            Stats st = GameManager.getInstance().getStats();
            Integer wins = st.getMainStats(gamer.getPlayerID()).get("Wins");
            if (wins != null) {
                wins++;
                if (wins % 30 == 0) {
                    keyType = KeyType.GAME_COSMETIC_KEY;
                } else if (wins % 10 == 0) {
                    keyType = KeyType.GAME_KEY;
                } else if (wins % 5 == 0) {
                    keyType = KeyType.DEFAULT_KEY;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return keyType;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.getWorld().getName().contains("endlobby"))
            if (player.getLocation().getBlockY() <= 0) PlayerUtil.redirectToHub(player);

    }
}
