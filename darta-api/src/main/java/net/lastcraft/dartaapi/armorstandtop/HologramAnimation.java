package net.lastcraft.dartaapi.armorstandtop;

import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.TimeUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public final class HologramAnimation implements Supplier<String> {

    private final Language language;
    private final int timeMinutes;

    private LocalDateTime nextTime;

    public HologramAnimation(Language language, int timeMinutes) {
        this.language = language;
        this.timeMinutes = timeMinutes;

        nextTime = LocalDateTime.now().plusMinutes(timeMinutes);
    }

    @Override
    public String get() {
        Duration between = Duration.between(LocalDateTime.now(), nextTime);
        if (between.isNegative() || between.isZero()) {
            nextTime = LocalDateTime.now().plusMinutes(timeMinutes);
            return language.getMessage("HOLO_TOP_UPDATE_NOW");
        }
        int millis = (int) between.toMillis();
        return language.getMessage("HOLO_TOP_UPDATE", TimeUtil.leftTime(language, millis));
    }
}
