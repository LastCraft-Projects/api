package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.WorldLoadEvent;

@Deprecated
public class WorldListener extends DListener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent e){
        if (GameSettings.minigame != MiniGameType.DEFAULT) e.getWorld().setAutoSave(false);
    }

}
