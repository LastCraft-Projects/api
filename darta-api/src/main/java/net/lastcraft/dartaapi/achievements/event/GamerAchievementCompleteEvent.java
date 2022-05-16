package net.lastcraft.dartaapi.achievements.event;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.event.gamer.GamerEvent;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.dartaapi.achievements.achievement.Achievement;
import org.bukkit.event.Cancellable;

@Getter
public final class GamerAchievementCompleteEvent extends GamerEvent implements Cancellable {

    private final Achievement achievement;
    @Setter
    private boolean cancelled;

    public GamerAchievementCompleteEvent(BukkitGamer gamer, Achievement achievement) {
        super(gamer);
        this.achievement = achievement;
    }
}
