package net.lastcraft.dartaapi.armorstandtop;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.hologram.Hologram;
import net.lastcraft.api.hologram.HologramAPI;
import net.lastcraft.base.locale.Language;
import org.bukkit.Location;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Top {

    protected static final HologramAPI HOLOGRAM_API = LastCraft.getHologramAPI();

    protected final TopManager standTopManager;
    protected final Location location;
    protected final TIntObjectMap<Hologram> holograms = new TIntObjectHashMap<>();

    /**
     * получить центральную голограмму
     * @param language - язык для которого ее получить
     * @return - голограмма
     */
    public final Hologram getHologramMiddle(Language language) {
        return holograms.get(language.getId());
    }

    public final List<StandTop> getStands() {
        return standTopManager.getAllStands(this);
    }

    /**
     * где находится топ
     * @return - где он
     */
    public final Location getLocation() {
        return location.clone();
    }

    /**
     * получить порядковый номер этого типа
     * @return - порядковый номер (нужен для БД)
     */
    public final int getId() {
        int id = 0;

        for (Top topType : standTopManager.getTops()) {
            if (topType == this) {
                break;
            }

            id++;
        }

        return id;
    }
}
