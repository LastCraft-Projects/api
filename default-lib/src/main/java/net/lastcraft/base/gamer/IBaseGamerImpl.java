package net.lastcraft.base.gamer;

import lombok.Getter;
import lombok.Setter;
import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.sections.*;
import net.lastcraft.base.skin.Skin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class IBaseGamerImpl implements IBaseGamer {

    protected final Map<String, Object> data = new HashMap<>();
    private final Map<String, Section> sections = new HashMap<>();

    @Getter
    @Setter
    protected String name;

    private long start = System.currentTimeMillis(); //когда началась загрузка данных(для дебага)

    IBaseGamerImpl(String name) {
        this.name = name;

        initSection(BaseSection.class);
        initSection(SkinSection.class);

        if (initSections() != null) {
            this.initSections().forEach(this::initSection);
        }
    }

    /**
     * в этом методе мы пишем дополнительные секции которые будут загружены
     */
    protected Set<Class<? extends Section>> initSections() {
        return null;
    }

    public final <T extends Section> void initSection(Class<T> clazz) {
        T section = null;
        try {
            section = clazz.getConstructor(IBaseGamer.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (section == null) {
            throw new IllegalArgumentException("кажется что-то пошло не так, секция - "
                    + clazz.getSimpleName() + ", ник игрока - " + name);
        }

        String nameSection = clazz.getSimpleName();
        sections.put(nameSection, section);
    }

    public final long getStart() {
        return start;
    }

    @Override
    public int getLevelNetwork() {
        NetworkingSection networkingSection = getSection(NetworkingSection.class);
        if (networkingSection == null) {
            return -1;
        }
        return networkingSection.getLevel();
    }

    @Override
    public int getExp() {
        NetworkingSection networkingSection = getSection(NetworkingSection.class);
        if (networkingSection == null) {
            return -1;
        }
        return networkingSection.getExp();
    }

    @Override
    public int getExpNextLevel() {
        NetworkingSection networkingSection = getSection(NetworkingSection.class);
        if (networkingSection == null) {
            return -1;
        }
        return networkingSection.getExpNextLevel();
    }

    @Override
    public String getDisplayName() {
        return "§r" + getPrefix() + getName();
    }

    @Override
    public Skin getSkin() {
        return getSection(SkinSection.class).getSkin();
    }

    @Override
    public int getPlayerID() {
        return getSection(BaseSection.class).getPlayerID();
    }

    @Override
    public Group getGroup() {
        return getSection(BaseSection.class).getGroup();
    }

    @Override
    public void setGroup(Group group) {
        setGroup(group, true);
    }

    //вызываем этот метод в коре, чтобы обновить группу игрока
    public void setGroup(Group group, boolean saveToMysql) {
        getSection(BaseSection.class).setGroup(group, saveToMysql);
        MoneySection section = getSection(MoneySection.class);
        if (section != null) {
            section.setMultiple(MoneySection.getMultiple(group));
        }
        FriendsSection friendsSection = getSection(FriendsSection.class);
        if (friendsSection != null) {
            friendsSection.setFriendsLimit(FriendsSection.getFriendsLimit(group));
        }
    }

    @Override
    public String getPrefix() {
        return getSection(BaseSection.class).getPrefix();
    }

    @Override
    public void setPrefix(String prefix) {
        getSection(BaseSection.class).setPrefix(prefix);
    }

    @Override
    public void addData(String name, Object data) {
        this.data.put(name.toLowerCase(), data);
    }

    @Override
    public <T> T getData(String name) {
        return (T) this.data.get(name.toLowerCase());
    }

    @Override
    public void clearData(String name) {
        this.data.remove(name.toLowerCase());
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public Map<String, Section> getSections() {
        return sections;
    }

    @Override
    public boolean isDonater() {
        int level = getGroup().getLevel();
        return level > Group.DEFAULT.getLevel()
                && level < Group.HELPER.getLevel()
                && getGroup() != Group.BUILDER;
    }

    @Override
    public boolean isStaff() {
        Group group = getGroup();
        return group == Group.ADMIN || group == Group.HELPER || group == Group.MODERATOR;
    }

    @Override
    public boolean isPlayer() {
        return getGroup() == Group.DEFAULT;
    }

    @Override
    public boolean isGold() {
        int level = getGroup().getLevel();
        return level >= Group.GOLD.getLevel();
    }

    @Override
    public boolean isDiamond() {
        int level = getGroup().getLevel();
        return level >= Group.DIAMOND.getLevel();
    }

    @Override
    public boolean isEmerald() {
        int level = getGroup().getLevel();
        return level >= Group.EMERALD.getLevel();
    }

    @Override
    public boolean isMagma() {
        int level = getGroup().getLevel();
        return level >= Group.MAGMA.getLevel();
    }

    @Override
    public boolean isShulker() {
        int level = getGroup().getLevel();
        return level >= Group.SHULKER.getLevel();
    }

    @Override
    public boolean isYouTube() {
        int level = getGroup().getLevel();
        return getGroup() == Group.YOUTUBE || level >= Group.ADMIN.getLevel();
    }

    @Override
    public boolean isBuilder() {
        int level = getGroup().getLevel();
        return getGroup() == Group.BUILDER || level >= Group.ADMIN.getLevel();
    }

    @Override
    public boolean isJunior() {
        int level = getGroup().getLevel();
        return level >= Group.JUNIOR.getLevel();
    }

    @Override
    public boolean isHelper() {
        int level = getGroup().getLevel();
        return level >= Group.HELPER.getLevel();
    }

    @Override
    public boolean isModerator() {
        int level = getGroup().getLevel();
        return level >= Group.MODERATOR.getLevel();
    }
}
