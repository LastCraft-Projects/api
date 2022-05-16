package net.lastcraft.dartaapi.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.api.event.DEvent;
import net.lastcraft.api.game.GameModeType;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
@Deprecated
public class PlayerChangeGamemodeEvent extends DEvent {

    private final Player player;
    private final GameModeType gameModeType;
}
