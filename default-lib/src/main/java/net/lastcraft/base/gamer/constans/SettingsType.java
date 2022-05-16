package net.lastcraft.base.gamer.constans;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;

@AllArgsConstructor
@Getter
public enum SettingsType {
    HIDER(1), //вырубить нахуй этих игроков
    CHAT(2), //не хочу видеть, что эти дауны поносят
    FLY(3), //не хочу летать, я пидор
    BLOOD(4), //че за ебаное дерьмо
    MUSIC(5), //ебаная музыка, в рот ее
    BOARD(6), //хуев скорборд, нахуя он нужен?
    HUB_GLOWING(7), //быть с подсветкой в хабе
    FRIENDS_REQUEST(8), //запросы в друзья
    PARTY_REQUEST(9),
    PRIVATE_MESSAGE(10), //личные сообщения для даунов
    GUILD_REQUEST(11), //запросы в парашу
    DONATE_CHAT(12), //чат даунов
    STAFF_CHAT(13), //чат челов, которые помогают серву делом, а не еблом
    TEAM_GLOWING(14), //будут ли подсвечиваться тиммейты или нет
    ;
    private static final TIntObjectMap<SettingsType> SETTINGS = new TIntObjectHashMap<>();

    public static final int LANGUAGE_KEY = 0; //айди который в БД

    private final int key;

    @Nullable
    public static SettingsType getSettingType(int key) {
        return SETTINGS.get(key);
    }

    static {
        for (SettingsType settingsType : values()) {
            SETTINGS.put(settingsType.key, settingsType);
        }
    }

}
