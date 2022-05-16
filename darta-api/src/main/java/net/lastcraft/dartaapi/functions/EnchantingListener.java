package net.lastcraft.dartaapi.functions;

import net.lastcraft.api.game.GameModeType;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.dartaapi.utils.DListener;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import net.lastcraft.packetlib.nms.interfaces.gui.DEnchantingTable;
import net.lastcraft.packetlib.nms.types.EnchantingSlot;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

public class EnchantingListener extends DListener {

    private final NmsManager nmsManager = NmsAPI.getManager();

    @EventHandler
    public void onEnchantingCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equalsIgnoreCase("/ie")){
            Player player = e.getPlayer();
            BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
            if (gamer.getGameMode() != GameModeType.SPECTATOR) {
                if (gamer.isDiamond()) {
                    DEnchantingTable table = nmsManager.getEnchantingTable(player);
                    table.addItem(EnchantingSlot.LAPIS, getLapis());
                    table.openGui();
                    e.setCancelled(true);
                } else {
                    gamer.sendMessageLocale("NO_PERMS_GROUP", Group.DIAMOND.getNameEn());
                    e.setCancelled(true);
                }
            } else {
                gamer.sendMessageLocale("ERROR_COMMAND_IN_GAME");
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onOpenInventory(InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.ENCHANTING) {
            Inventory inventory = e.getInventory();
            inventory.setItem(EnchantingSlot.LAPIS.getSlot(), getLapis());
        }
    }


    @EventHandler
    public void onEnchantment(EnchantItemEvent e) {
        Inventory inventory = e.getInventory();
        inventory.setItem(EnchantingSlot.LAPIS.getSlot(), getLapis());
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.ENCHANTING) {
            int slot = e.getRawSlot();
            if (slot == 1) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getType() == InventoryType.ENCHANTING) {
            EnchantingInventory inventory = (EnchantingInventory)e.getInventory();
            inventory.setSecondary(null);
        }
    }

    private ItemStack getLapis() {
        Dye d = new Dye();
        d.setColor(DyeColor.BLUE);
        ItemStack i = d.toItemStack();
        i.setAmount(3);
        return i;
    }
}
