package net.lastcraft.api.event.gamer;

import lombok.Getter;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.gamer.constans.SettingsType;

@Getter
public class GamerChangeSettingEvent extends GamerEvent {

    private final SettingsType setting;
    private final boolean result;

    public GamerChangeSettingEvent(BukkitGamer gamer, SettingsType setting, boolean result) {
        super(gamer);
        this.setting = setting;
        this.result = result;
    }
}
