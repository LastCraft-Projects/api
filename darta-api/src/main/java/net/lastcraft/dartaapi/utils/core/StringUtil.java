package net.lastcraft.dartaapi.utils.core;

import net.lastcraft.base.locale.Language;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Deprecated
public class StringUtil {

    public static String onPercentBar(double currentValue, double startValue) {
        String currentColor = "§3";
        String totalColor = "§8";
        double length = 50.0D;
        double progress = currentValue / startValue * length;
        return currentColor + StringUtils.repeat("|", (int)progress) + totalColor + StringUtils.repeat("|", (int) (length - progress));
    }

    public static int onPercent(int val, int max) {
        return (val * 100) / max;
    }

    public static String onPercentString(int val, int max) {
        return onPercent(val, max) + "%";
    }

    public static String getCompleteTime(int time) {
        return net.lastcraft.base.util.StringUtil.getCompleteTime(time);
    }

    public static String getUTFNumber(int number){
        return net.lastcraft.base.util.StringUtil.getUTFNumber(number);
    }

    public static String getRomanNumber(int number){
        return net.lastcraft.base.util.StringUtil.getRomanNumber(number);
    }

    public static void sendDamageCause(Player death) {
        Player killer = death.getKiller();

        EntityDamageEvent entityDamage = death.getLastDamageCause();

        if (death == killer || entityDamage == null) {
            sendToPlayerKill(death, null, "DAMAGE_CAUSE_SUCIDE");
            return;
        }

        EntityDamageEvent.DamageCause cause = entityDamage.getCause();

        if (cause != null) {
            if (cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_EXPLOSION_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_EXPLOSION");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.CONTACT) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_CACTUS_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_CACTUS");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.DROWNING) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_SWIM_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_SWIM");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.FALL) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_DROP_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_DROP");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_BURN_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_BURN");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.LAVA) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_SWIM2_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_SWIM2");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.MAGIC) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_MAGIC");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_BOW_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_BOW");
                    return;
                }
            }

            if (cause == EntityDamageEvent.DamageCause.VOID) {
                if (killer != null) {
                    sendToPlayerKill(death, killer, "DAMAGE_CAUSE_OUT_TO");
                    return;
                } else {
                    sendToPlayerKill(death, null, "DAMAGE_CAUSE_OUT");
                    return;
                }
            }
        }

        if (killer != null) {
            sendToPlayerKill(death, killer, "DAMAGE_CAUSE_KILLED_TO");
        } else {
            sendToPlayerKill(death, null, "DAMAGE_CAUSE_KILLED");
        }
    }


    private static void sendToPlayerKill(Player death, Player killer, String string) {
        String killerDisplay = "";
        if (killer != null )
            killerDisplay = killer.getDisplayName();
        String deathDisplay = death.getDisplayName();

        MessageUtil.alertMsg(true, string, deathDisplay, killerDisplay);
    }

    @Deprecated
    public static String getCorrectWord(int time, String main, String endingOne, String endingTwo, String endingTree) {
        String moneyString = String.valueOf(time);
        if (moneyString.endsWith("1") && !moneyString.endsWith("11")) {
            return main + endingOne;
        } else if ((moneyString.endsWith("2") && !moneyString.endsWith("12")) || (moneyString.endsWith("3") && !moneyString.endsWith("13")) || (moneyString.endsWith("4") && !moneyString.endsWith("14"))) {
            return main + endingTwo;
        } else {
            return main + endingTree;
        }
    }

    @Deprecated
    public static String getCorrectWord(int time, String text, int locale) {
        return net.lastcraft.base.util.StringUtil.getCorrectWord(time, text, Language.getLanguage(locale));
    }

    public static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String changeEnding(String word, char ending){
        return word.substring(0, word.length()-1) + ending;
    }

    public static String stringToCenter(String text) {
        return net.lastcraft.base.util.StringUtil.stringToCenter(text);
    }

    private static int textLength(String text) {
        int count = 0;
        char[] array = text.toCharArray();
        for (char symbol : array) {
            if (symbol == '§') {
                count += 2;
            }
        }
        return text.length() - count;
    }

    public static List<String> getAnimation(String displayName) {
        return net.lastcraft.base.util.StringUtil.getAnimation(displayName);
    }

    private static int getSizeCharArray(char[] array) {
        int i = 0;
        for (char arr : array) {
            if (arr == 0) {
                i++;
            }
        }
        return i;
    }

    public static String getLineCode(int line) {
        return net.lastcraft.base.util.StringUtil.getLineCode(line);
    }

    public static String getNumberFormat(int amount) {
        return net.lastcraft.base.util.StringUtil.getNumberFormat(amount);
    }
}
