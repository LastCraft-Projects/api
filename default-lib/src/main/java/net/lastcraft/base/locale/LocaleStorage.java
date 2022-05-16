package net.lastcraft.base.locale;

import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class LocaleStorage {

    static Locale RU_LOCALE = new Locale("ru");
    static Locale EN_LOCALE = new Locale("en");
    static Locale UA_LOCALE = new Locale("ua");

    public static void updateLocales() { //обновление локализаций
        for (Language language : Language.values()) {
            try {
                language.getLocale().loadFromSite();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
