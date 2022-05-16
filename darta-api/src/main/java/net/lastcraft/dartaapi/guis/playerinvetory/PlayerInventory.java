package net.lastcraft.dartaapi.guis.playerinvetory;

import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import net.lastcraft.dartaapi.utils.inventory.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class PlayerInventory extends BukkitRunnable {

    private static Map<String, Inventory> playerInventorys;

    static Collection<Inventory> getInventorys(){
        return playerInventorys.values();
    }

    static void removePlayerInventory(Player player){
        playerInventorys.remove(player.getName());
    }

    public static Inventory getInventory(Player player){
        return playerInventorys.get(player.getName());
    }

    public void startPI(){
        this.runTaskTimer(DartaAPI.getInstance(), 0, 20);
    }

    public void stopPI(){
        this.cancel();
        playerInventorys.clear();
    }

    public PlayerInventory(){
        for (Player player : PlayerUtil.getAlivePlayers()){
            playerInventorys.put(player.getName(), Bukkit.createInventory(null, 54, player.getName()));
        }
    }

    @Override
    public void run() {
        playerInventorys.entrySet().stream().filter(inventory ->
                PlayerUtil.isAlive(Bukkit.getPlayerExact(inventory.getKey()))).forEach(inventory ->
                        updatePlayerInventory(Bukkit.getPlayerExact(inventory.getKey()), inventory.getValue()));
    }

    private void updatePlayerInventory(Player player, Inventory inventory){
        inventory.clear();

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        ItemStack[] contents = player.getInventory().getContents();
        int slot = 0;

        for (int i = 0; i<armorContents.length; i++){
            inventory.setItem(i, armorContents[i]);
        }
        for (int i = 9; i <= 17; i++) {
            inventory.setItem(i, ItemUtil.createItemStack(Material.STAINED_GLASS_PANE, (short) 14, "§c"));
        }
        for (int i = 53; i >= 18; i--) {
            inventory.setItem(i, contents[slot]);
            slot++;
        }
    }

    static {
        playerInventorys = new ConcurrentHashMap<>();
    }
}
