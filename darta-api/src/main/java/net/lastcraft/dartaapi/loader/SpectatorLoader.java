package net.lastcraft.dartaapi.loader;

import net.lastcraft.api.LastCraft;
import net.lastcraft.api.player.BukkitGamer;
import net.lastcraft.api.player.GamerManager;
import net.lastcraft.base.sql.ConnectionConstants;
import net.lastcraft.base.sql.SQLConnection;
import net.lastcraft.dartaapi.game.spectator.SPlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Deprecated
public class SpectatorLoader {
    private SQLConnection connection;
    private static final GamerManager GAMER_MANAGER = LastCraft.getGamerManager();

    public SpectatorLoader() {
        connection = new SQLConnection("mysql" + ConnectionConstants.DOMAIN.getValue(),
                "root", ConnectionConstants.PASSWORD.getValue(), "newcore");
        connect();
    }

    private void connect() {
        connection.execute("CREATE TABLE IF NOT EXISTS `spectator_settings` (`id` INT(11) PRIMARY KEY, `speed` INT, `fly` INT, `spectate` INT, `vision` INT)");
    }

    public void addSetting(Player player) {
        BukkitGamer gamer = GAMER_MANAGER.getGamer(player);
        SPlayer sPlayer = SPlayer.getSPlayer(player);
        connection.execute("UPDATE `spectator_settings` SET `speed`=" + sPlayer.getSpeedSpec() + ",`fly`=" + sPlayer.getAlwaysFly() + ",`spectate`=" + sPlayer.getHideSpectators() + ",`vision`=" + sPlayer.getVision() + " WHERE `id`=" + gamer.getPlayerID() + ";");
    }

    public int[] getStats(int playerID) {
        int[] result = { 0, 0, 0, 0 };
        try {
            Statement statement = connection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `spectator_settings` WHERE `id`='" + playerID + "'");
            if (rs.next()) {
                result[0] = rs.getInt("speed");
                result[1] = rs.getInt("fly");
                result[2] = rs.getInt("spectate");
                result[3] = rs.getInt("vision");
            } else {
                connection.execute("INSERT INTO `spectator_settings` (`id`, `speed`, `fly`, `spectate`, `vision`) VALUES ('" + playerID + "', 0, 0, 0, 0);");
            }
            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}