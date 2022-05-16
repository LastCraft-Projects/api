package net.lastcraft.packetlib.nms;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.lastcraft.packetlib.nms.interfaces.NmsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@UtilityClass
public class NmsAPI {

    @Getter
    private NmsManager manager;

    public void init(JavaPlugin plugin) {
        if (manager != null) {
            return;
        }

        try {
            manager = (NmsManager) Class.forName("net.lastcraft.packetlib.nms.v1_12_R1.NmsManager_v1_12_R1")
                    .getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getServer().getConsoleSender().sendMessage("§4NMS API not found, Plugin DartaAPI disabled (");
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.setWhitelist(true);
        }
    }
}
