package net.lastcraft.base.gamer.constans;

import gnu.trove.TCollections;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.lastcraft.base.locale.Language;

@AllArgsConstructor
@Getter
public enum Group {
    DEFAULT(0, "§7Игрок", "§7Default", "default", "§7", "§8 »§7", 0, 2),

    GOLD(100, "§e§lGOLD", "§e§lGOLD", "gold", "§e§lGOLD §e", "§8 »§7", 1, 6),
    DIAMOND(200, "§b§lDIAMOND", "§b§lDIAMOND", "diamond", "§b§lDIAMOND §b", "§8 »§7", 2, 7),
    EMERALD(300, "§a§lEMERALD", "§a§lEMERALD", "emerald", "§a§lEMERALD §a", "§8 »§7", 3, 8),
    MAGMA(400, "§c§lMAGMA", "§c§lMAGMA", "magma", "§c§lMAGMA §c", "§8 »§7", 4, 9),

    SHULKER(450, "§7§lSHULKER", "§7§lSHULKER", "shulker", "§7§lSHULKER §7", "§8 »§f", 5, 10),
    YOUTUBE(500, "§6§lYouTube", "§6§lYouTube", "youtube", "§6§lYouTube §6", "§8 »§f", 6, 11),
    JUNIOR(650, "§2Мл.Хелпер", "§2§lJUNIOR", "junior", "§2§lJUNIOR §2", "§8 »§f", 7, 12),

    BUILDER(600, "§3Строитель", "§3§lBUILDER", "builder", "§3§lBUILDER §3", "§8 »§f", 5, 13),
    HELPER(700, "§2Хелпер", "§2§lHELPER", "helper", "§2§lHELPER §2", "§8 »§f", 8, 5),
    MODERATOR(800, "§9Модератор", "§9§lMODERATOR", "moderator", "§9§lMODER §9", "§8 »§f", 9, 4),

    ADMIN(900, "§4Админ", "§4§lADMIN", "administrator", "§4§lADMIN §4", "§8 »§f", 10, 3);

    private final int id;
    private final String name;
    private final String nameEn;
    private final String groupName;
    private final String prefix;
    private final String suffix;
    private final int level;
    private final int forumGroup;

    public String getLocaleName(Language language) {
        if (language == Language.RUSSIAN) {
            return name;
        }

        return nameEn;
    }

    private static TIntObjectMap<Group> GROUPS = TCollections.synchronizedMap(new TIntObjectHashMap<>());

    public static Group getGroup(int groupID) {
        Group group = GROUPS.get(groupID);
        if (group != null) {
            return group;
        }

        return DEFAULT;
    }

    public static Group getGroupByLevel(int level) {
        return GROUPS.valueCollection().stream()
                .filter(g -> g.getLevel() == level)
                .findFirst()
                .orElse(Group.DEFAULT);
    }

    public static Group getGroupByName(String name) {
        return GROUPS.valueCollection().stream()
                .filter(g -> g.getGroupName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(Group.DEFAULT);
    }

    @Override
    public String toString() {
        return nameEn;
    }

    static {
        for (Group group : values()) {
            GROUPS.put(group.getId(), group);
        }
    }
}
