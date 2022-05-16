package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;

@Deprecated
public class ItemSpawnListener extends DListener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e){
        if (GameSettings.minigame != MiniGameType.DEFAULT) e.setCancelled(true);
        else if (e.getEntity().getItemStack().getType() == Material.WEB) e.setCancelled(true);
    }
}
