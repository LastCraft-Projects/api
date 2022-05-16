package net.lastcraft.base.gamer;

import net.lastcraft.base.gamer.constans.Group;
import net.lastcraft.base.gamer.sections.Section;
import net.lastcraft.base.skin.Skin;

import java.util.Map;

public interface IBaseGamer {
    int getPlayerID();

    String getName();
    void setName(String name);

    String getDisplayName();
    Skin getSkin();

    Group getGroup();
    void setGroup(Group group);

    String getPrefix();
    void setPrefix(String prefix);

    void addData(String name, Object data);
    <T> T getData(String name);
    void clearData(String name);
    Map<String, Object> getData();

    int getExp();
    int getExpNextLevel();
    int getLevelNetwork();

    /**
     * онлайн или оффлайн игрок
     * @return - онлайн или нет
     */
    boolean isOnline();

    /**
     * удлалить из памяти
     */
    void remove();

    Map<String, Section> getSections();
    default <T extends Section> T getSection(Class<T> sectionClass) {
        return (T) getSections().get(sectionClass.getSimpleName());
    }

    boolean isDeveloper();

    boolean isDonater();
    boolean isStaff();
    boolean isTester();

    boolean isPlayer();
    boolean isGold();
    boolean isDiamond();
    boolean isEmerald();
    boolean isMagma();
    boolean isShulker();
    boolean isYouTube();
    boolean isBuilder();
    boolean isJunior();
    boolean isHelper();
    boolean isModerator();
    boolean isAdmin();
}
