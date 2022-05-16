package net.lastcraft.dartaapi.utils;

import lombok.experimental.UtilityClass;
import net.lastcraft.base.gamer.constans.Version;
import org.bukkit.entity.Player;
import protocolsupport.api.ProtocolSupportAPI;

@UtilityClass
public class ProtocolSupportUtil {

    public Version getVersion(Player player) {
        return Version.getVersion(ProtocolSupportAPI.getProtocolVersion(player).getId());
    }
}
