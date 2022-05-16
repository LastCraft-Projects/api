package net.lastcraft.dartaapi.listeners;

import net.lastcraft.api.game.GameSettings;
import net.lastcraft.api.game.MiniGameType;
import net.lastcraft.dartaapi.utils.DListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkUnloadEvent;

@Deprecated
public class ChunkListener extends DListener {

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent e){
        if (GameSettings.minigame == MiniGameType.SURVIVAL)
            return;
        e.setCancelled(true);
    }

}
