package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.api.util.InventoryUtil;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.dartaapi.utils.bukkit.ExplodeUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;

@Deprecated
public class TNTListener extends DListener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(PlayerInteractEvent e) {
        if (!e.hasItem()) {
            return;
        }

        Player player = e.getPlayer();
        if (e.getItem().getType() == Material.TNT) {
            e.setCancelled(true);
            if (e.getHand() == EquipmentSlot.HAND) {
                InventoryUtil.removeItemByMaterial(player.getInventory(), Material.TNT, 1);
            } else {
                ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
                itemInOffHand.setAmount(itemInOffHand.getAmount() - 1);
            }

            Location loc = e.getClickedBlock().getLocation().add(0, 1.0, 0);
            TNTPrimed tnt = loc.getWorld().spawn(loc, TNTPrimed.class);
            tnt.setMetadata("owner", new FixedMetadataValue(DartaAPI.getInstance(), e.getPlayer()));
            tnt.setFuseTicks(40);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void Explosion(EntityExplodeEvent event) {
        if (GameSettings.minigame == MiniGameType.LW_SOLO) {
            ExplodeUtils explosion = new ExplodeUtils(event.blockList(), true, true, true, Arrays.asList("BARRIER", "OBSIDIAN", "CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "BEDROCK"));
            explosion.run();
        }
    }
}
