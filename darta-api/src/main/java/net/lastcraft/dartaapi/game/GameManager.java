package net.lastcraft.dartaapi.game;

import net.lastcraft.dartaapi.game.module.StartModule;
import net.lastcraft.dartaapi.loader.SpectatorLoader;
import net.lastcraft.dartaapi.stats.Stats;
import net.lastcraft.dartaapi.utils.DListener;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public abstract class GameManager {
    private static GameManager instance;

    public static GameManager getInstance() {
        return instance;
    }

    private Stats stats;
    private SpectatorLoader spectatorLoader;

    protected GameManager() {
        instance = this;
        this.loadConfig();
        List<String> columns = new ArrayList<>();
        columns.add("Games");
        columns.addAll(this.getColumns());
        this.stats = new Stats(this.getTable(), columns);
        this.spectatorLoader = new SpectatorLoader();
    }

    public void addGameListener(DListener listener) {
        StartModule.getGameListeners().add(listener);
    }

    public Stats getStats() {
        return stats;
    }

    public SpectatorLoader getSpectatorLoaders() {
        return spectatorLoader;
    }

    protected abstract String getTable();
    protected abstract List<String> getColumns();

    protected abstract void loadConfig();
}
