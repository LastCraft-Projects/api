package net.lastcraft.dartaapi.stats;

import lombok.Getter;
import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.sql.ConnectionConstants;
import net.lastcraft.base.sql.SQLConnection;
import net.lastcraft.dartaapi.utils.core.CoreUtil;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Deprecated
public class Stats {

    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    private SQLConnection connection;
    private String table;
    @Getter
    private List<String> stats;
    @Getter
    private Map<Player, StatsPlayer> statsPlayers = new HashMap<>();

    public Stats(String table, Collection<String> stats) {
        this.table = table;
        this.stats = new ArrayList<>();
        this.stats.addAll(stats);
        connect();
    }

    public void connect(){
        connection = new SQLConnection("s3" + ConnectionConstants.DOMAIN.getValue(), "root",
                ConnectionConstants.PASSWORD.getValue(), "Stats");
        StringBuilder req = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + table + "` ("
                + "`ID` INT AUTO_INCREMENT PRIMARY KEY, `PlayerID` INT NOT NULL UNIQUE");
        for (String column : stats)
            req.append(",`")
               .append(column)
               .append("` MEDIUMINT DEFAULT '0'");

        req.append(");");
        connection.execute(req.toString());
    }

    public void save() {
        try {
            Statement statement = connection.getConnection().createStatement();

            for (StatsPlayer stats : statsPlayers.values()) {
                StringBuilder reg = new StringBuilder("UPDATE `" + table + "` SET ");
                for (Map.Entry<String, Integer> column : stats.getStats().entrySet())
                    reg.append("`")
                       .append(column.getKey())
                       .append("`=`")
                       .append(column.getKey())
                       .append("`+'")
                       .append(column.getValue())
                       .append("', ");

                reg = new StringBuilder(reg.substring(0, reg.length() - 2));
                reg.append("WHERE `PlayerID`='")
                   .append(stats.getPlayerID())
                   .append("';");
                statement.executeUpdate(reg.toString());
            }

            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, Integer> getMainStats(int playerId) {
        Map<String, Integer> stats = new HashMap<>();
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `" + this.table + "` WHERE `PlayerID`='" + playerId + "' LIMIT 1;");
            if (rs.next()) {
                for (String string : this.stats) {
                    stats.put(string, rs.getInt(string));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

    public void close() {
        try {
            save();
            statsPlayers.clear();
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createPlayerStats(Player player){
        int playerID = GAMER_MANAGER.getGamer(player).getPlayerID();
        statsPlayers.put(player, new StatsPlayer(player, playerID));
        connection.execute("INSERT INTO `" + this.table + "` (`PlayerID`) VALUES ('" + playerID + "') ON DUPLICATE KEY UPDATE `PlayerID`=`PlayerID`;");
    }

    public int getPlayerStats(Player player, String column) {
        return statsPlayers.get(player).getStats(column);
    }

    public Map<Player, Integer> getPlayersTop(String column) {
        Map<Player, Integer> playersTop = new HashMap<>();

        for (Map.Entry<Player, StatsPlayer> statsPlayer : statsPlayers.entrySet()) {
            playersTop.put(statsPlayer.getKey(), statsPlayer.getValue().getStats(column));
        }

        return CoreUtil.sortByValue(playersTop);
    }

    public void addPlayerStats(Player player, String column, int amount) {
        statsPlayers.get(player).addStats(column, amount);
    }
}
