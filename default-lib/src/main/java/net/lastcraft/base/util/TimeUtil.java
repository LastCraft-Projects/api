package net.lastcraft.base.util;

import lombok.experimental.UtilityClass;
import net.lastcraft.base.locale.Language;

import java.util.List;

@UtilityClass
public final class TimeUtil {

    public String leftTime(Language language, long unixTime, boolean future) {
        if (unixTime < 0L)
            return language.getMessage("ETERNITY");

        long seconds;

        if(!future) {
            seconds = System.currentTimeMillis() - unixTime;
        } else {
            seconds = unixTime - System.currentTimeMillis();
        }

        seconds /= 1000L;
        seconds = seconds + 1; //FIX

        if (seconds < 0)
            return language.getMessage("UNKNOWN");

        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long i;

        if (seconds >= 60L) {
            i = (long) ((int) (seconds / 60L));
            minutes = i;
            seconds %= 60L;
        }

        if (minutes >= 60L) {
            i = (long) ((int) (minutes / 60L));
            hours = i;
            minutes %= 60L;
        }

        if (hours >= 24L) {
            i = (long) ((int) (hours / 24L));
            days = i;
            hours %= 24L;
        }

        List<String> day = language.getList("TIME_DAY_1");
        List<String> hour = language.getList("TIME_HOURS_1");
        List<String> min = language.getList("TIME_MINUTES_1");
        List<String> sec = language.getList("TIME_SECOND_2");

        String s = "", msg;
        if(days > 0L) {
            msg = formatTime(days, day.get(0), day.get(1), day.get(2), day.get(3));
            s = s + days + " " + msg + " ";
        }

        if(hours > 0L) {
            msg = formatTime(hours, hour.get(0), hour.get(1), hour.get(2), hour.get(3));
            s = s + hours + " " + msg + " ";
        }

        if(minutes > 0L) {
            msg = formatTime(minutes, min.get(0), min.get(1), min.get(2), min.get(3));
            s = s + minutes + " " + msg + " ";
        }

        if(seconds > 0L) {
            msg = formatTime(seconds, sec.get(0), sec.get(1), sec.get(2), sec.get(3));
            s = s + seconds + " " + msg + " ";
        }

        if (s.length() > 0)
            s = s.substring(0, s.length() - 1);

        return s;

    }

    public String leftTime(Language language, long millis) {
        if (millis < 0L)
            return language.getMessage("ETERNITY");

        long seconds = millis / 1000;

        long minutes = 0L;
        long hours = 0L;
        long days = 0L;
        long i;

        if (seconds >= 60L) {
            i = (long) ((int) (seconds / 60L));
            minutes = i;
            seconds %= 60L;
        }

        if (minutes >= 60L) {
            i = (long) ((int) (minutes / 60L));
            hours = i;
            minutes %= 60L;
        }

        if (hours >= 24L) {
            i = (long) ((int) (hours / 24L));
            days = i;
            hours %= 24L;
        }

        List<String> day = language.getList("TIME_DAY_1");
        List<String> hour = language.getList( "TIME_HOURS_1");
        List<String> min = language.getList("TIME_MINUTES_1");
        List<String> sec = language.getList("TIME_SECOND_2");

        String s = "", msg;
        if(days > 0L) {
            msg = formatTime(days, day.get(0), day.get(1), day.get(2), day.get(3));
            s = s + days + " " + msg + " ";
        }

        if(hours > 0L) {
            msg = formatTime(hours, hour.get(0), hour.get(1), hour.get(2), hour.get(3));
            s = s + hours + " " + msg + " ";
        }

        if(minutes > 0L) {
            msg = formatTime(minutes, min.get(0), min.get(1), min.get(2), min.get(3));
            s = s + minutes + " " + msg + " ";
        }

        if(seconds > 0L) {
            msg = formatTime(seconds, sec.get(0), sec.get(1), sec.get(2), sec.get(3));
            s = s + seconds + " " + msg + " ";
        }

        if (s.length() > 0)
            s = s.substring(0, s.length() - 1);

        return s;
    }

    private String formatTime(long num, String main, String single, String lessFive, String others) {
        int end = (int)(num % 10L);
        if(num % 100L <= 10L || num % 100L >= 15L) {
            switch(end) {
            case 1:
                return main + single;
            case 2:
            case 3:
            case 4:
                return main + lessFive;
            }
        }

        return main + others;
    }
}