package net.lastcraft.base.locale;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.base.util.StringUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
public enum Language {
    RUSSIAN(0, "Русский", LocaleStorage.RU_LOCALE, "RUSSIA"),
    ENGLISH(1, "English", LocaleStorage.EN_LOCALE, "ENGLISH"),
    UKRAINE(2, "Українська", LocaleStorage.UA_LOCALE, "UKRAINE");

    private final int id;
    private final String name;
    private final Locale locale;
    private final String headName;

    /**
     * сколько % перевода сделано
     * @return - сколько процентов переведено
     */
    public int getPercent() {
        if (this == DEFAULT) {
            return 100;
        }

        int size = this.locale.getSize();

        return StringUtil.onPercent(size, DEFAULT.getLocale().getSize());
    }

    public List<String> getList(String key, Object... replaced) {
        List<String> list = locale.getListMessages().get(key);

        if (list == null) {
            list = DEFAULT.locale.getListMessages().get(key);

            if (list == null) {
                list = Collections.singletonList(ERROR);

                System.out.println("[Localization] Невозможно найти локализацию с ключом " + key);
            }
        }

        if (replaced.length == 0) {
            return list;
        }

        return format(list, replaced);
    }

    public String getMessage(String key, Object... replaced) {
        String message = locale.getMessages().get(key);

        if (message == null) {
            message = DEFAULT.locale.getMessages().get(key);

            if (message == null) {
                message = ERROR;

                System.out.println("[Localization] Невозможно найти локализацию с ключом " + key);
            }
        }

        if (replaced.length == 0) {
            return message;
        }

        return String.format(message, replaced);
    }

    @Override
    public String toString() {
        return "Language{name=" + name + "}";
    }

    private static List<String> format(List<String> list, Object... objects) {
        String string = String.join("±", list);
        string = String.format(string, objects);
        return Arrays.asList(string.split("±"));
    }

    private static final TIntObjectMap<Language> LOCALES = new TIntObjectHashMap<>();
    private static final Language DEFAULT = RUSSIAN;
    private static final String ERROR = "§cNot found";

    public static Language getLanguage(int id) {
        Language language = LOCALES.get(id);
        if (language != null) {
            return language;
        }

        return DEFAULT;
    }

    public static Language getDefault() {
        return DEFAULT;
    }

    static {
        for (Language locale : values()) {
            LOCALES.put(locale.id, locale);
        }
    }
}
