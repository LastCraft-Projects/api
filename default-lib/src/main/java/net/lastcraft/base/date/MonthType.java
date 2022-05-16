package net.lastcraft.base.date;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.base.locale.Language;

import java.util.Calendar;

@AllArgsConstructor
@Getter
public enum MonthType {
    JANUARY(0),
    FEBRUARY(1),
    MARCH(2),
    APRIL(3),
    MAY(4),
    JUNE(5),
    JULY(6),
    AUGUST(7),
    SEPTEMBER(8),
    OCTOBER(9),
    NOVEMBER(10),
    DECEMBER(11);

    private final int id;

    public String getName(Language language) {
        return language.getMessage(this.name());
    }

    private static final TIntObjectMap<MonthType> MONTHS = new TIntObjectHashMap<>();

    /**
     * получить месяц который сейчас
     * @return - месяц
     */
    public static MonthType getMonth() {
        return MONTHS.get(Calendar.getInstance().get(Calendar.MONTH));
    }

    public static MonthType getMonth(Calendar calendar) {
        return MONTHS.get(calendar.get(Calendar.MONTH));
    }

    static {
        for (MonthType monthType : values()) {
            MONTHS.put(monthType.id, monthType);
        }
    }
}
