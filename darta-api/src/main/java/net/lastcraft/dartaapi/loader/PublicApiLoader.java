package net.lastcraft.dartaapi.loader;

import net.lastcraft.entity.DartaGamerManager;
import net.lastcraft.packetlib.libraries.*;
import net.lastcraft.packetlib.libraries.command.CommandsAPIImpl;
import net.lastcraft.packetlib.libraries.effect.ParticleAPIImpl;
import net.lastcraft.packetlib.libraries.entity.EntityAPIImpl;
import net.lastcraft.packetlib.libraries.hologram.HologramAPIImpl;
import net.lastcraft.packetlib.libraries.inventory.InventoryAPIImpl;
import net.lastcraft.packetlib.libraries.scoreboard.ScoreBoardAPIImpl;
import net.lastcraft.packetlib.libraries.usableItem.UsableAPIImpl;
import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

final class PublicApiLoader {

    static void init(DartaAPI dartaApi) {
        Object api = null;
        try {
            Class<?> apiClass = Class.forName("net.lastcraft.api.LastCraft");
            Constructor<?> constructor = apiClass.getConstructor();
            api = constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            dartaApi.getServer().getConsoleSender().sendMessage("§4API not found, Plugin DartaAPI disabled (");
            Bukkit.getPluginManager().disablePlugin(dartaApi);
            Bukkit.setWhitelist(true);
        }
        if (api == null) {
            return;
        }

        try {
            setStaticField(api, "jsonMessageAPI", new JSONMessageAPIImpl());
            setStaticField(api, "gamerManagerAPI", new DartaGamerManager(dartaApi));
            setStaticField(api, "soundAPI", new SoundAPIImpl());
            setStaticField(api, "usableAPI", new UsableAPIImpl(dartaApi));
            setStaticField(api, "entityAPI", new EntityAPIImpl(dartaApi));
            setStaticField(api, "hologramAPI", new HologramAPIImpl(dartaApi));
            setStaticField(api, "scoreBoardAPI", new ScoreBoardAPIImpl(dartaApi));
            setStaticField(api, "commandsAPI", new CommandsAPIImpl());
            setStaticField(api, "inventoryAPI", new InventoryAPIImpl(dartaApi));
            setStaticField(api, "titleAPI", new TitleAPIImpl());
            setStaticField(api, "borderAPI", new BorderAPIImpl());
            setStaticField(api, "actionBarAPI", new ActionBarAPIImpl());
            setStaticField(api, "particleAPI", new ParticleAPIImpl(dartaApi));
            setStaticField(api, "coreAPI", new CoreAPIImpl(dartaApi));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setStaticField(Object instance, String fieldName, Object value) throws Exception {
        Class clazz = instance.getClass();
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, value);
        f.setAccessible(false);
    }
}
