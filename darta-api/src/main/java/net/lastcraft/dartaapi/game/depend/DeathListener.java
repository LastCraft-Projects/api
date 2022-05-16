package net.lastcraft.dartaapi.game.depend;

import net.lastcraft.api.BorderAPI;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.event.game.PlayerKillEvent;
import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.TeamManager;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.GamerAPI;
import net.lastcraft.base.gamer.GamerBase;
import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.base.util.Cooldown;
import net.lastcraft.dartaapi.game.team.SelectionTeam;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Deprecated
public class DeathListener extends DListener {

    private final BorderAPI borderAPI = LastCraft.getBorderAPI();

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerKillEvent playerKillEvent = PlayerKillEvent.getPlayerKiller(player);
        if (playerKillEvent != null)
            playerKillEvent.getKills().clear();

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player death = e.getEntity();
        e.setDeathMessage(null);
        death.setHealth(20.0);

        if (GameSettings.canDropOnDeath) {
            Location location = death.getLocation();
            for (ItemStack item : e.getDrops())
                location.getWorld().dropItem(location, item.clone());

        }
        e.getDrops().clear();
        PlayerUtil.death(death);
        PlayerKillEvent event = new PlayerKillEvent(death, death.getKiller());
        BukkitUtil.callEvent(event);

        death.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        Player death = (Player) e.getEntity();
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            e.setCancelled(true);
            if (PlayerUtil.isSpectator(death))
                return;
            if (Cooldown.hasCooldown(death.getName(), "death"))
                return;
            Cooldown.addCooldown(death.getName(), "death", 20);
            PlayerUtil.death(death);
            death.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 1));
            death.setLastDamageCause(new EntityDamageEvent(death, EntityDamageEvent.DamageCause.VOID, 0));
            PlayerKillEvent event = new PlayerKillEvent(death, death.getKiller());
            BukkitUtil.callEvent(event);
        }
    }

    @EventHandler
    public void onDamagePlayer(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;

        Player player = (Player)e.getEntity();
        if (PlayerUtil.isSpectator(player))
            return;

        if (player.getGameMode() == GameMode.ADVENTURE)
            return;

        if (e instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent edbeEvent = (EntityDamageByEntityEvent)e;
            Entity entityDamager = edbeEvent.getDamager();
            if (entityDamager instanceof Player) {
                Player damager = (Player) entityDamager;
                if (checkTeam(player, damager))
                    return;
            }
            if (entityDamager instanceof ThrownPotion) {
                ThrownPotion potion = (ThrownPotion) entityDamager;
                if (potion.getShooter() instanceof Player) {
                    Player damager = (Player) potion.getShooter();
                    if (checkTeam(player, damager))
                        return;
                }
            }
            if (entityDamager instanceof Arrow) {
                Arrow arrow = (Arrow) entityDamager;
                if (arrow.getShooter() instanceof Player) {
                    Player damager = (Player) arrow.getShooter();
                    if (checkTeam(player, damager))
                        return;
                }
            }
        }
        borderAPI.sendRedScreen(player);
        playSimpleBlood(player.getLocation());
    }

    private boolean checkTeam(Player player, Player damager) {
        if (PlayerUtil.isSpectator(damager))
            return true;

        if (GameSettings.teamMode) {
            TeamManager team1 = SelectionTeam.getSelectedTeams().get(player);
            TeamManager team2 = SelectionTeam.getSelectedTeams().get(damager);
            return team1 != null && team2 != null && team1.getTeam().equals(team2.getTeam());
        }
        return false;
    }

    private void playSimpleBlood(Location location) {
        for (GamerBase base : GamerAPI.getGamers().values()) {
            BukkitGamer gamer = (BukkitGamer) base;
            Player p = gamer.getPlayer();
            if (p == null || !p.isOnline())
                continue;
            if (!gamer.getSetting(SettingsType.BLOOD))
                continue;
            p.playEffect(location.clone().add(0, 0.5, 0), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        }
   }
}
