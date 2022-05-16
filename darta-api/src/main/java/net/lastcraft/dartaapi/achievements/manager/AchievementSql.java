package net.lastcraft.dartaapi.achievements.manager;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.base.sql.ConnectionConstants;
import net.lastcraft.base.sql.api.MySqlDatabase;
import net.lastcraft.base.sql.api.query.MysqlQuery;
import net.lastcraft.base.sql.api.query.QuerySymbol;
import net.lastcraft.base.sql.api.table.ColumnType;
import net.lastcraft.base.sql.api.table.TableColumn;
import net.lastcraft.base.sql.api.table.TableConstructor;
import net.lastcraft.dartaapi.achievements.achievement.Achievement;
import net.lastcraft.dartaapi.achievements.achievement.AchievementPlayerData;

public final class AchievementSql {

    private final AchievementManager achievementManager;
    private final MySqlDatabase mySqlDatabase;

    AchievementSql(AchievementManager achievementManager, String database, String host) {
        this.achievementManager = achievementManager;
        mySqlDatabase = MySqlDatabase.newBuilder()
                .data(database)
                .host(host)
                .password(ConnectionConstants.PASSWORD.getValue())
                .user("root")
                .create();

        initTables();
    }

    private void initTables() {
        new TableConstructor("CompleteAchievement",
                new TableColumn("id", ColumnType.INT_11).autoIncrement(true).primaryKey(true),
                new TableColumn("ach_id", ColumnType.INT_2),
                new TableColumn("player_id", ColumnType.INT_11)
        ).create(mySqlDatabase);
        new TableConstructor("ProcessAchievement",
                new TableColumn("id", ColumnType.INT_11).autoIncrement(true).primaryKey(true),
                new TableColumn("player_id", ColumnType.INT_11),
                new TableColumn("ach_id", ColumnType.INT_2),
                new TableColumn("key", ColumnType.VARCHAR_16),
                new TableColumn("result", ColumnType.INT)
        ).create(mySqlDatabase);

        //добавляем индексы чтобы бд не грузила
        //mySqlDatabase.execute("ALTER TABLE `CompleteAchievement` ADD INDEX (`id`);");
        //mySqlDatabase.execute("ALTER TABLE `CompleteAchievement` ADD INDEX (`ach_id`);");
        //mySqlDatabase.execute("ALTER TABLE `CompleteAchievement` ADD INDEX (`player_id`);");
        //mySqlDatabase.execute("ALTER TABLE `ProcessAchievement` ADD INDEX (`id`);");
        //mySqlDatabase.execute("ALTER TABLE `ProcessAchievement` ADD INDEX (`ach_id`);");
        //mySqlDatabase.execute("ALTER TABLE `ProcessAchievement` ADD INDEX (`player_id`);");
        //mySqlDatabase.execute("ALTER TABLE `ProcessAchievement` ADD INDEX (`key`);");
    }

    public void addComplete(int playerID, TIntObjectMap<AchievementPlayerData> achievementsData, Achievement achievement) {
        if (playerID == -1)
            return;

        mySqlDatabase.execute(MysqlQuery.insertTo("CompleteAchievement")
                .set("ach_id", achievement.getId())
                .set("player_id", playerID));
        AchievementPlayerData achievementPlayerData = achievementsData.get(achievement.getId());
        if (achievementPlayerData == null)
            return;

        if (achievementPlayerData.isEmpty())
            return;

        mySqlDatabase.execute(MysqlQuery.deleteFrom("ProcessAchievement")
                .where("player_id", QuerySymbol.EQUALLY, playerID)
                .where("ach_id", QuerySymbol.EQUALLY, achievement.getId()));
    }

    public TIntObjectMap<AchievementPlayerData> getAchievementsData(BukkitGamer gamer) {
        if (gamer == null || gamer.getPlayerID() == -1)
            return new TIntObjectHashMap<>();

        int playerID = gamer.getPlayerID();
        return mySqlDatabase.executeQuery(MysqlQuery.selectFrom("ProcessAchievement")
                .where("player_id", QuerySymbol.EQUALLY, playerID), (rs) -> {
            TIntObjectMap<AchievementPlayerData> map = new TIntObjectHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("ach_id");
                Achievement achievement = achievementManager.getAchievement(id);
                if (achievement == null)
                    continue;

                AchievementPlayerData achievementPlayerData = map.get(id);
                if (achievementPlayerData == null) {
                    achievementPlayerData = new AchievementPlayerData(achievement);
                    map.put(id, achievementPlayerData);
                }

                String key = rs.getString("key");
                int value = rs.getInt("result");
                achievementPlayerData.addCachedInfo(key, value);
            }

            return map;
        });
    }

    public TIntObjectMap<Achievement> getCompleteData(BukkitGamer gamer) {
        if (gamer == null || gamer.getPlayerID() == -1)
            return new TIntObjectHashMap<>();

        int playerID = gamer.getPlayerID();
        return mySqlDatabase.executeQuery(MysqlQuery.selectFrom("CompleteAchievement")
                .where("player_id", QuerySymbol.EQUALLY, playerID), (rs) -> {
            TIntObjectMap<Achievement> map = new TIntObjectHashMap<>();
            while (rs.next()) {
                int id = rs.getInt("ach_id");
                Achievement achievement = achievementManager.getAchievement(id);
                if (achievement == null)
                    continue;

                map.put(id, achievement);
            }

            return map;
        });
    }

    public void insertData(int playerID, Achievement achievement, String key, int value) {
        if (playerID == -1)
            return;

        mySqlDatabase.execute(MysqlQuery.insertTo("ProcessAchievement")
                .set("ach_id", achievement.getId())
                .set("key", key)
                .set("player_id", playerID)
                .set("result", value));
    }

    public void updateData(int playerID, Achievement achievement, String key, int value) {
        if (playerID == -1)
            return;

        mySqlDatabase.execute(MysqlQuery.update("ProcessAchievement")
                .where("ach_id", QuerySymbol.EQUALLY, achievement.getId())
                .where("key", QuerySymbol.EQUALLY, key)
                .where("player_id", QuerySymbol.EQUALLY, playerID)
                .set("result", value)
                .limit());
    }
}
