package net.lastcraft.dartaapi.game.perk;

import net.lastcraft.api.event.game.PlayerKillEvent;
import net.lastcraft.dartaapi.game.perk.type.bowbreakblock.ArrowBlockBreakListener;
import net.lastcraft.dartaapi.game.perk.type.vampirism.VampListener;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.BlockIterator;

public class VanillaPerkListener extends DListener {

    @EventHandler
    public void onKill(PlayerKillEvent e) {
        if (!(e.getKiller() instanceof Player))
            return;
        Player killer = (Player) e.getKiller();
        if (VampListener.getPlayerVampirism().containsKey(killer.getName())) {
            double health = killer.getHealth();
            double n = health + (double) VampListener.getPlayerVampirism().get(killer.getName());
            if (n > killer.getMaxHealth()) {
                killer.setHealth(killer.getMaxHealth());
            }
            else killer.setHealth(n);
            killer.sendMessage("§aЗдоровье восполнено!");
        }
    }
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            World world = arrow.getWorld();
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                if (!ArrowBlockBreakListener.getPlayerArrowPerk().contains(shooter.getName())) return;
                BlockIterator bi = new BlockIterator(world, arrow.getLocation().toVector(), arrow.getVelocity().normalize(), 0, 4);
                Block hit = null;

                while (bi.hasNext()) {
                    hit = bi.next();
                    if (hit.getType() != Material.AIR && hit.getType().isBlock() && hit.getType().isSolid()) {
                        break;
                    }
                }

                assert hit != null;
                if (hit.getType() == Material.CHEST) return;
                if (hit.getType() == Material.TRAPPED_CHEST) return;
                if (hit.getType() == Material.LAVA) return;
                if (hit.getType() == Material.WATER) return;
                if (hit.getType() == Material.OBSIDIAN) return;

                final Location loc = hit.getLocation();
                for (Player nearby : PlayerUtil.getNearbyPlayers(hit.getLocation(), 10)) {
                    if (nearby.getLocation().getWorld().equals(loc.getWorld()))
                        nearby.playSound(nearby.getLocation(), ArrowBlockBreakListener.PlaySound(hit), 10.0f, 1.0f);
                }
                arrow.remove();
                hit.breakNaturally();
                loc.getWorld().playEffect(loc, Effect.SMOKE, 0);
                //loc.getWorld().playEffect(loc, Effect.ITEM_BREAK, hit.getTypeId(), hit.getData());
                hit.setType(Material.AIR);
            }
        }
    }

    public static void clearListeners() {
        VampListener.getPlayerVampirism().clear();
        ArrowBlockBreakListener.getPlayerArrowPerk().clear();
    }
}
