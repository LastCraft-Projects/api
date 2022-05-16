package net.lastcraft.dartaapi.game.spectator;

import net.lastcraft.api.ActionBarAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.event.game.EndGameEvent;
import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.api.scoreboard.PlayerTag;
import net.lastcraft.api.scoreboard.ScoreBoardAPI;
import net.lastcraft.dartaapi.event.PlayerChangeGamemodeEvent;
import net.lastcraft.dartaapi.game.GameManager;
import net.lastcraft.dartaapi.gamemodes.GameModeScoreBoardTeam;
import net.lastcraft.dartaapi.guis.playerinvetory.PlayerInventory;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.interfaces.packet.entityplayer.PacketCamera;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SpectatorListener extends DListener {
    private static final Map<String, BukkitTask> mapTaskClosestPlayer = new ConcurrentHashMap<>();

    private final NmsManager nmsManager = NmsAPI.getManager();
    private final ActionBarAPI actionBarAPI = LastCraft.getActionBarAPI();
    private final PacketContainer container = NmsAPI.getManager().getPacketContainer();
    private final GamerManager gamerManager = LastCraft.getGamerManager();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockCanBuild(BlockCanBuildEvent e) {
        Block block = e.getBlock();
        if (block != null) {
            Location blockLoc = block.getLocation();
            for (Player spectator : PlayerUtil.getSpectators()) {
                Location spectatorLoc = spectator.getLocation();
                if(blockLoc.getWorld() == spectatorLoc.getWorld() && blockLoc.distance(spectatorLoc) <= 2) {
                    e.setBuildable(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (PlayerUtil.isSpectator(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
           e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPotionSplash(PotionSplashEvent e) {
        for (LivingEntity entity : e.getAffectedEntities()) {
            if (entity instanceof Player) {
                if (PlayerUtil.isSpectator((Player) entity)) {
                    e.setIntensity(entity, 0);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (PlayerUtil.isSpectator(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (!PlayerUtil.isSpectator(player)) return;
            if (e.getCause() == EntityDamageEvent.DamageCause.FIRE || e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                nmsManager.disableFire(player);
            }
            e.setCancelled(true);
        }
    }

    //Исправить (ADVENTURE мод!)
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
            e.setCancelled(true);
            ItemStack hand = e.getItem();
            if (hand == null) return;
            if (e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_AIR) {
                if (hand.getType() == Material.COMPASS) {
                    SPlayer sPlayer = SPlayer.getSPlayer(player);
                    Player nearPlayer = sPlayer.getNearPlayer();
                    if (nearPlayer != null) player.teleport(nearPlayer.getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onPressShift(PlayerToggleSneakEvent e) {
        Player player = e.getPlayer();
        if (!e.isSneaking()) return;

        for (String aliveName : SpectatorMenu.CAMERA.keySet()) {
            Player alive = Bukkit.getPlayer(aliveName);
            if (alive == null) continue;
            if (!SpectatorMenu.CAMERA.get(aliveName).equals(player.getName()))
                continue;
            SpectatorMenu.CAMERA.remove(aliveName);
            player.teleport(alive);
            removeSpectator(player);
            break;
        }
    }

    @EventHandler
    public void onEndGame(EndGameEvent e) {
        for (Player player : PlayerUtil.getSpectators()) {
            if (!SpectatorMenu.CAMERA.containsValue(player.getName()))
                continue;
            removeSpectator(player);
        }
    }

    @EventHandler //в режиме спектра запрещаем менять слот
    public void onChangeSlot(PlayerItemHeldEvent e) {
        String name = e.getPlayer().getName();
        if (SpectatorMenu.CAMERA.containsValue(name)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChangeGamemode(PlayerChangeGamemodeEvent e) {
        Player player = e.getPlayer();
        GameModeType gameModeType = e.getGameModeType();

        if (gameModeType != GameModeType.SPECTATOR) return;

        String spectatorName = SpectatorMenu.CAMERA.remove(player.getName());
        if (spectatorName == null) return;
        Player spectator = Bukkit.getPlayer(spectatorName);
        if (spectator == null) return;

        spectator.teleport(player);
        removeSpectator(spectator);
    }

    private void removeSpectator(Player spectator) {
        for (SPlayer sPlayer : SPlayer.sPlayers.values()) {
            if (sPlayer.getHideSpectators() == 0) {
                Player player = sPlayer.getPlayer();
                player.showPlayer(spectator);
            }
        }
        BukkitGamer gamer = gamerManager.getGamer(spectator);
        if (gamer == null)
            return;
        actionBarAPI.sendBar(spectator, gamer.getLanguage().getMessage("SPECTATOR_CAMERA_OFF"));
        PacketCamera packet = container.getCameraPacket(spectator);
        packet.sendPacket(spectator);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();

        if (PlayerUtil.isSpectator(player)) {
            GameManager.getInstance().getSpectatorLoaders().addSetting(player);
            SPlayer.removeSPlayer(player);
            for (String alivePlayerName : SpectatorMenu.CAMERA.values()) {
                String name = SpectatorMenu.CAMERA.get(alivePlayerName);
                if (name == null) continue;
                if (name.equals(player.getName())) {
                    SpectatorMenu.CAMERA.remove(player.getName());
                }
            }
        } else {
            String whoName = SpectatorMenu.CAMERA.remove(player.getName());
            if (whoName != null) {
                Player who = Bukkit.getPlayer(whoName);
                if (who != null) {
                    removeSpectator(who);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        String whoName = SpectatorMenu.CAMERA.get(player.getName());
        if (whoName != null) {
            Player who = Bukkit.getPlayer(whoName);
            if (who != null) {
                if (player.getWorld() == who.getWorld() && who.getLocation().distance(player.getLocation()) > 10) {
                    who.teleport(player);
                }
            }
        }

        if (!PlayerUtil.isSpectator(player))
            return;

        try {
            if (SPlayer.getSPlayer(player).getAlwaysFly() == 1) {
                player.setFlying(true);
            }
            if (player.getLocation().getBlockY() <= 0) {
                player.setFlying(true);
                player.teleport(GameSettings.spectatorLoc);
            }
        } catch (Exception ignored) {}

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        BukkitGamer gamer = gamerManager.getGamer(player);
        if (gamer == null) {
            return;
        }

        gamer.setGameMode(GameModeType.SPECTATOR);
        player.teleport(GameSettings.spectatorLoc);

        SPlayer sPlayer = SPlayer.getSPlayer(player);

        if (sPlayer.getHideSpectators() == 1) {
            sPlayer.setHideSpectators(1);
        } else if (sPlayer.getHideSpectators() == 0) {
            sPlayer.setHideSpectators(0);
        }

        sPlayer.getSpectatorSettings().updateInventory();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAsyncJoin(AsyncGamerJoinEvent e) {
        Player joiner = e.getGamer().getPlayer();
        if (joiner == null) {
            return;
        }
        GameModeScoreBoardTeam.onJoin(joiner);

        final ScoreBoardAPI API = LastCraft.getScoreBoardAPI();

        PlayerTag playerTag = API.createTag("lol");
        playerTag.setPrefix("§c");
        playerTag.addPlayersToTeam(PlayerUtil.getAlivePlayers());
        playerTag.sendTo(joiner);

        for (Player player : PlayerUtil.getSpectators()) {
            if (player == joiner) continue;

            playerTag = API.createTag("lol");
            playerTag.setPrefix("§c");
            playerTag.addPlayersToTeam(PlayerUtil.getAlivePlayers());
            playerTag.sendTo(player);
        }
    }



    @EventHandler
    public void onEntityTarget(EntityTargetEvent e) {
        Entity entity = e.getTarget();
        if (entity instanceof Player){
            if (PlayerUtil.isSpectator((Player) entity)){
                e.setCancelled(true);
                e.setTarget(null);
            }
        }
    }

    @EventHandler
    public void onBoat(VehicleDamageEvent e) { //запретить ломать лодки
        Entity attacker = e.getAttacker();
        if (!(attacker instanceof Player)) {
            return;
        }

        e.setCancelled(PlayerUtil.isSpectator((Player) attacker));
    }

    @EventHandler
    public void onBoat(VehicleEntityCollisionEvent e) { //запретить двигать лодку
        Entity entity = e.getEntity();
        if (!(entity instanceof Player)) {
            return;
        }

        if (PlayerUtil.isSpectator((Player) entity)) {
            e.setCollisionCancelled(true);
            e.setCancelled(true);
            e.setPickupCancelled(true);
        }

    }

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player) {
            Player player = (Player) e.getTarget();
            if (PlayerUtil.isSpectator(player)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        if (PlayerUtil.isSpectator(player)) {
            e.setCancelled(true);
            if (e.getRightClicked() instanceof Player) {
                Player victim = (Player) e.getRightClicked();
                if (PlayerUtil.isAlive(victim)) {
                    Inventory inventory = PlayerInventory.getInventory(victim);
                    if (inventory == null)
                        return;
                    player.openInventory(inventory);
                }
            }
        }
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        this.runClosestPlayer(player, player.getItemInHand());
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());
        this.runClosestPlayer(player, item);
    }

    private void runClosestPlayer(final Player player, ItemStack itemInHand) {
        if(itemInHand != null && itemInHand.getType() == Material.COMPASS) {
            if(Bukkit.getOnlinePlayers().size() > 0) {
                if(mapTaskClosestPlayer.get(player.getName()) == null) {
                    BukkitTask bt = Bukkit.getScheduler().runTaskTimer(DartaAPI.getInstance(), () -> findNearPlayer(player), 0L, 10L);
                    mapTaskClosestPlayer.put(player.getName(), bt);
                }
            }
        } else {
            if(mapTaskClosestPlayer.get(player.getName()) != null) {
                mapTaskClosestPlayer.get(player.getName()).cancel();
            }

            mapTaskClosestPlayer.remove(player.getName());
            actionBarAPI.sendBar(player, "");
        }
    }

    private void findNearPlayer(Player player) {
        Iterator<? extends Player> iterator = Bukkit.getOnlinePlayers().iterator();
        Player nearPlayer = null;
        double minDistance = 1.8D;

        while(iterator.hasNext()) {
            Player newPlayer = iterator.next();
            if(newPlayer != player) {
                BukkitGamer gamer = gamerManager.getGamer(player);
                BukkitGamer ndPlayer = gamerManager.getGamer(newPlayer);
                if(gamer != null && ndPlayer.getGameMode() != GameModeType.SPECTATOR) {
                    try {
                        double newDistance = player.getLocation().distance(newPlayer.getLocation());
                        if (newDistance <= minDistance) {
                            minDistance = newDistance;
                            nearPlayer = newPlayer;
                        }
                    } catch (IllegalArgumentException ignored) { }
                }
            }
        }

        if(nearPlayer != null) {
            BukkitGamer gamer = gamerManager.getGamer(player);
            SPlayer sPlayer = SPlayer.getSPlayer(player);
            BukkitGamer dnPlayer = gamerManager.getGamer(nearPlayer);
            if(gamer != null && dnPlayer != null && dnPlayer.getGameMode() != GameModeType.SPECTATOR) {
                if(gamer.getGameMode() == GameModeType.SPECTATOR) {
                    LastCraft.getActionBarAPI().sendBar(player, gamer.getLanguage().getMessage("COMPASS_MESSAGE_SPECTATOR",
                            "§r" + dnPlayer.getPrefix() + nearPlayer.getName(),
                            String.valueOf(round(minDistance))));
                }

                player.setCompassTarget(nearPlayer.getLocation());
                if (sPlayer != null) sPlayer.setNearPlayer(nearPlayer);
            }
        }
    }

    private static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}