package net.lastcraft.dartaapi.loader;

import net.lastcraft.base.sql.ConnectionConstants;
import net.lastcraft.base.sql.SQLConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Deprecated
public class StatsLoader {
    private SQLConnection connection;

    public StatsLoader(){
        connection = new SQLConnection("s3" + ConnectionConstants.DOMAIN.getValue(),
                "root", ConnectionConstants.PASSWORD.getValue(), "Stats");
    }

    public int[] getStats(String table, int playerID){
        int[] stats = null;

        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `" + table + "` WHERE `PlayerID` = '" + playerID + "' LIMIT 1;");
            int size = rs.getMetaData().getColumnCount();
            stats = new int[size-2];
            if (rs.next()){
                for (int i = 3; i<=size; i++){
                    stats[i-3] = rs.getInt(i);
                }
            } else {
                for (int i = 0; i<size-2; i++){
                    stats[i] = 0;
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stats;
    }

    public void close(){
        try {
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
