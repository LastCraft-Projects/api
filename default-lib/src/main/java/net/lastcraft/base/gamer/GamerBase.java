package net.lastcraft.base.gamer;

import com.google.common.collect.ImmutableSet;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.constans.SettingsType;
import net.lastcraft.base.gamer.sections.Section;
import net.lastcraft.base.gamer.sections.SettingsSection;
import net.lastcraft.base.locale.Language;

import java.util.List;
import java.util.Set;

public abstract class GamerBase extends IBaseGamerImpl implements OnlineGamer {

    private static final Set<String> DEVELOPERS = ImmutableSet.of(
            "CatCoder",
            "IBROI",
            "Kambet",
            "VariationTime",
            "CTAC0H"
    );

    protected GamerBase(String name) {
        super(name);

        initSection(SettingsSection.class);

        for (Section section : getSections().values()) {
            if (section.loadData()) {
                continue;
            }

            throw new IllegalArgumentException("кажется что-то пошло не так, секция - "
                    + section.getClass().getSimpleName() + ", ник игрока - " + name);
        }
    }

    public void sendMessages(List<String> messages) {
        messages.forEach(this::sendMessage);
    }

    public void sendMessageLocale(String key, Object... replaced) {
        sendMessage(this.getLanguage().getMessage(key, replaced));
    }

    public void sendMessagesLocale(String key, Object... replaced) {
        sendMessages(this.getLanguage().getList(key, replaced));
    }

    @Override
    public boolean isOnline() {
        return true;
    }

    @Override
    public boolean isDeveloper() {
        return getGroup() == Group.ADMIN && DEVELOPERS.contains(this.name);
    }

    @Override
    public Language getLanguage() {
        return getSection(SettingsSection.class).getLanguage();
    }

    @Override
    public String toString() {
        return "OnlineGamer{name=" + this.getName() + '}';
    }

    @Override
    public void remove() {
        GamerAPI.removeGamer(name);
    }

    @Override
    public boolean isAdmin() {
        return getGroup() == Group.ADMIN;
    }

    @Override
    public boolean isTester() {
        return getPrefix().toLowerCase().contains("test");
    }

    public boolean getSetting(SettingsType type) {
        return getSection(SettingsSection.class).getSetting(type);
    }

    public void setSetting(SettingsType type, boolean key) {
        SettingsSection settingsSection = getSection(SettingsSection.class);
        if (key == settingsSection.getSetting(type)) {
            return;
        }

        settingsSection.setSetting(type, key, true);
    }
}
