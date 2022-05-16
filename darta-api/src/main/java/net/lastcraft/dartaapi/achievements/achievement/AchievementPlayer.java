package net.lastcraft.dartaapi.achievements.achievement;

import gnu.trove.TCollections;
import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import lombok.Getter;
import lombok.NonNull;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.SoundType;
import net.lastcraft.base.locale.Language;
import net.lastcraft.base.util.StringUtil;
import net.lastcraft.dartaapi.achievements.event.GamerAchievementCompleteEvent;
import net.lastcraft.dartaapi.achievements.manager.AchievementManager;
import net.lastcraft.dartaapi.utils.bukkit.BukkitUtil;

public final class AchievementPlayer {

    @Getter
    private final BukkitGamer gamer;

    private final AchievementManager achievementManager;

    private final TIntObjectMap<Achievement> completed;
    private final TIntObjectMap<AchievementPlayerData> achievementsData;

    public AchievementPlayer(BukkitGamer gamer,
                             AchievementManager achievementManager) {
        this.gamer = gamer;
        this.achievementManager = achievementManager;
        this.completed = achievementManager.getAchievementSql().getCompleteData(gamer);
        this.achievementsData = achievementManager.getAchievementSql().getAchievementsData(gamer);
    }

    public boolean hasAchievement(int id) {
        return completed.containsKey(id);
    }

    public boolean hasAchievement(@NonNull Achievement achievement) {
        return hasAchievement(achievement.getId());
    }

    public AchievementPlayerData getAchievementsData(Achievement achievement) {
        if (achievement == null || !achievementManager.getAchievements().containsKey(achievement.getId()))
            return null;

        AchievementPlayerData achievementPlayerData = achievementsData.get(achievement.getId());
        if (achievementPlayerData != null) {
            return achievementPlayerData;
        }

        achievementPlayerData = new AchievementPlayerData(achievement);
        achievementsData.put(achievement.getId(), achievementPlayerData);

        return achievementPlayerData;
    }

    public AchievementPlayerData getAchievementsData(int id) {
        return getAchievementsData(achievementManager.getAchievement(id));
    }

    public TIntObjectMap<Achievement> getCompleted() {
        return TCollections.unmodifiableMap(completed);
    }

    public void complete(Achievement achievement) {
        if (hasAchievement(achievement) || gamer == null) {
            return;
        }

        GamerAchievementCompleteEvent event = new GamerAchievementCompleteEvent(gamer, achievement);
        BukkitUtil.callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        achievementManager.getAchievementSql().addComplete(gamer.getPlayerID(), achievementsData, achievement);

        completed.put(achievement.getId(), achievement);
        achievementsData.remove(achievement.getId());

        Language lang = gamer.getLanguage();
        gamer.sendMessage("");
        gamer.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        gamer.sendMessage("                     " + lang.getMessage("ACHIEVEMENT_COMPLETE"));
        gamer.sendMessage("");
        gamer.sendMessage(StringUtil.stringToCenter("§a" + achievement.getName(lang).toUpperCase()));
        lang.getList(achievement.getLoreKey()).forEach(text -> gamer.sendMessage(StringUtil.stringToCenter(text)));
        gamer.sendMessage("");
        gamer.sendMessage("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        gamer.sendMessage("");
        gamer.playSound(SoundType.CHEST_OPEN);

        achievement.complete(gamer);
    }

    public void save() {
        if (gamer == null) {
            return;
        }

        for (TIntObjectIterator<AchievementPlayerData> iterator = achievementsData.iterator(); iterator.hasNext();) {
            iterator.advance();
            iterator.value().save(gamer.getPlayerID(), achievementManager.getAchievementSql());
        }

    }
}
