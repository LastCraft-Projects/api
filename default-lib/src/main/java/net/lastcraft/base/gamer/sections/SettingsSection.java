package net.lastcraft.base.gamer.sections;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.Getter;
import net.lastcraft.base.gamer.IBaseGamer;
import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.sql.GlobalLoader;
import net.lastcraft.base.util.Pair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SettingsSection extends Section {

    @Getter
    private Language language = Language.getDefault();

    private final Map<SettingsType, Boolean> settings = new ConcurrentHashMap<>();

    //для БД, чтобы не делать 10 запросов в БД, тут мы храним те данные, которые в самой БД
    private final TIntObjectMap<Integer> duplicatedSettings = TCollections.synchronizedMap(new TIntObjectHashMap<>());

    public SettingsSection(IBaseGamer baseGamer) {
        super(baseGamer);
    }

    @Override
    public boolean loadData() {
        Pair<Pair<Language, Boolean>, Map<SettingsType, Boolean>> settings = GlobalLoader.getSettings(baseGamer.getPlayerID());
        this.language = settings.getFirst().getFirst();

        if (settings.getFirst().getSecond()) { //проверяем, из БД ли эта инфа или нет
            duplicatedSettings.put(SettingsType.LANGUAGE_KEY, this.language.getId());
        }

        for (Map.Entry<SettingsType, Boolean> data : settings.getSecond().entrySet()) {
            SettingsType settingsType = data.getKey();
            boolean value = data.getValue();

            duplicatedSettings.put(data.getKey().getKey(), data.getValue() ? 1 : 0);

            if (settingsType == SettingsType.FLY && !baseGamer.isDiamond()) {
                this.settings.put(settingsType, false);
                continue;
            }

            if (settingsType == SettingsType.MUSIC && !baseGamer.isEmerald()) {
                this.settings.put(settingsType, false);
                continue;
            }

            if (settingsType == SettingsType.TEAM_GLOWING && !baseGamer.isEmerald()) {
                this.settings.put(settingsType, false);
                continue;
            }

            if (settingsType == SettingsType.HUB_GLOWING && !baseGamer.isDiamond()) {
                this.settings.put(settingsType, false);
                continue;
            }

            this.settings.put(settingsType, value);
        }

        this.settings.putIfAbsent(SettingsType.CHAT, true);
        this.settings.putIfAbsent(SettingsType.BOARD, true);
        this.settings.putIfAbsent(SettingsType.PRIVATE_MESSAGE, true);
        this.settings.putIfAbsent(SettingsType.FRIENDS_REQUEST, true);
        this.settings.putIfAbsent(SettingsType.GUILD_REQUEST, true);
        this.settings.putIfAbsent(SettingsType.PARTY_REQUEST, true);

        if (!baseGamer.isPlayer()) {
            this.settings.putIfAbsent(SettingsType.DONATE_CHAT, true);
        }

        if (baseGamer.isDiamond()) {
            this.settings.putIfAbsent(SettingsType.FLY, true);
        }

        if (baseGamer.isEmerald()) {
            this.settings.putIfAbsent(SettingsType.MUSIC, true);
        }

        return true;
    }

    public boolean getSetting(SettingsType type) {
        if (type == null) {
            return false;
        }
        return settings.getOrDefault(type, false);
    }

    public void setSetting(SettingsType type, boolean value, boolean saveToMysql) {
        if (type == null) {
            return;
        }

        Boolean saveValue = settings.get(type);
        if (saveValue != null && saveValue == value) {
            return;
        }

        settings.put(type, value);

        if (saveToMysql) {
            save(type.getKey(), (value ? 1 : 0));
        }

        this.duplicatedSettings.put(type.getKey(), (value ? 1 : 0));
    }

    public void setLang(Language language, boolean saveToMysql) {
        if (this.language.getId() == language.getId()) {
            return;
        }

        this.language = language;

        if (saveToMysql) {
            save(SettingsType.LANGUAGE_KEY, language.getId());
        }

        this.duplicatedSettings.put(SettingsType.LANGUAGE_KEY, language.getId());
    }

    private void save(int type, int value) {
        int playerID = baseGamer.getPlayerID();
        GlobalLoader.setSetting(playerID, type, value, !duplicatedSettings.containsKey(type));
    }

    /*
    static {
        new TableConstructor("settings",
                new TableColumn("id", ColumnType.INT_11).primaryKey(true).unigue(true),
                new TableColumn("setting_id", ColumnType.INT_5).primaryKey(true),
                new TableColumn("setting_value", ColumnType.INT_5)
        ).create(GlobalLoader.getMysqlDatabase()); // + добавить интексы
    }
    */
}