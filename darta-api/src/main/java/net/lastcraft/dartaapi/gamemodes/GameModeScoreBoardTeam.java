package net.lastcraft.dartaapi.gamemodes;

import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.interfaces.packet.scoreboard.PacketScoreBoardTeam;
import net.lastcraft.packetlib.nms.scoreboard.DTeam;
import net.lastcraft.packetlib.nms.scoreboard.TeamAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Deprecated
public class GameModeScoreBoardTeam {
    private static final PacketContainer PACKET_CONTAINER = NmsAPI.getManager().getPacketContainer();
    private static DTeam spectatorTeam;
    private static DTeam ghostTeam;

    static void addPlayerSpectatorTeam(Player player) {
        if (spectatorTeam == null) {
            spectatorTeam = new DTeam("0Spectators");
            spectatorTeam.setSetFriendInv(true);
            spectatorTeam.setPrefix("§8[§4✖§8] §7");
        }

        spectatorTeam.addPlayer(player.getName());
        PacketScoreBoardTeam packet1 = PACKET_CONTAINER.getScoreBoardTeamPacket(spectatorTeam, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = PACKET_CONTAINER.getScoreBoardTeamPacket(spectatorTeam, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = PACKET_CONTAINER.getScoreBoardTeamPacket(spectatorTeam, TeamAction.PLAYERS_ADD);
        Bukkit.getOnlinePlayers().forEach(pl -> PACKET_CONTAINER.sendPacket(pl, packet1, packet2, packet3));
    }

    static void removePlayerSpectatorTeam(Player player) {
        removePlayerTeam(spectatorTeam, player);
    }

    static void addPlayerGhostTeam(Player player) {
        if (ghostTeam == null) {
            ghostTeam = new DTeam("0Ghosts");
            ghostTeam.setSetFriendInv(true);
            ghostTeam.setPrefix("§7");
        }
        ghostTeam.addPlayer(player.getName());
        PacketScoreBoardTeam packet1 = PACKET_CONTAINER.getScoreBoardTeamPacket(ghostTeam, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = PACKET_CONTAINER.getScoreBoardTeamPacket(ghostTeam, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = PACKET_CONTAINER.getScoreBoardTeamPacket(ghostTeam, TeamAction.PLAYERS_ADD);
        Bukkit.getOnlinePlayers().forEach(pl -> PACKET_CONTAINER.sendPacket(pl, packet1, packet2, packet3));
    }

    static void removePlayerGhostTeam(Player player) {
        removePlayerTeam(ghostTeam, player);
    }

    public static void onJoin(Player player) {
        if (spectatorTeam != null)
            send(player, spectatorTeam);

        if (ghostTeam != null)
            send(player, ghostTeam);
    }

    private static void send(Player player, DTeam team) {
        PacketScoreBoardTeam packet1 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_ADD);
        PACKET_CONTAINER.sendPacket(player, packet1, packet2, packet3);
    }

    public static void resetBoard() {
        if (spectatorTeam != null) {
            PacketScoreBoardTeam packet = PACKET_CONTAINER.getScoreBoardTeamPacket(spectatorTeam, TeamAction.REMOVE);
            Bukkit.getOnlinePlayers().forEach(pl -> PACKET_CONTAINER.sendPacket(pl, packet));
            spectatorTeam.getPlayers().clear();
        }
        if (ghostTeam != null) {
            PacketScoreBoardTeam packet = PACKET_CONTAINER.getScoreBoardTeamPacket(ghostTeam, TeamAction.REMOVE);
            Bukkit.getOnlinePlayers().forEach(pl -> PACKET_CONTAINER.sendPacket(pl, packet));
            ghostTeam.getPlayers().clear();
        }
    }

    private static void removePlayerTeam(DTeam team, Player player) {
        if (team == null) {
            return;
        }

        team.removePlayer(player.getName());
        PacketScoreBoardTeam packet1 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.CREATE);
        PacketScoreBoardTeam packet2 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.UPDATE);
        PacketScoreBoardTeam packet3 = PACKET_CONTAINER.getScoreBoardTeamPacket(team, TeamAction.PLAYERS_REMOVE);
        Bukkit.getOnlinePlayers().forEach(pl -> PACKET_CONTAINER.sendPacket(pl, packet1, packet2, packet3));
    }

}
