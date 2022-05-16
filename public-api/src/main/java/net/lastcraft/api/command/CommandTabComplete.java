package net.lastcraft.api.command;

import net.lastcraft.api.player.GamerEntity;

import java.util.List;

public interface CommandTabComplete {

    /**
     * Выполнить табкомплит
     */
    List<String> getComplete(GamerEntity gamerEntity, String alias, String[] args);
}
