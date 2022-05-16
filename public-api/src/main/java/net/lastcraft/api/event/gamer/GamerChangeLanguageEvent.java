package net.lastcraft.api.event.gamer;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.locale.Language;
import org.bukkit.event.Cancellable;

@Getter
public class GamerChangeLanguageEvent extends GamerEvent implements Cancellable {

    private final Language language;
    private final Language oldLanguage;

    @Setter
    private boolean cancelled;

    public GamerChangeLanguageEvent(BukkitGamer gamer, Language language, Language oldLanguage) {
        super(gamer);
        this.language = language;
        this.oldLanguage = oldLanguage;
    }
}
