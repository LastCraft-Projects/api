package net.lastcraft.api.event.game;

import net.lastcraft.api.event.DEvent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Deprecated
public class EndGameEvent extends DEvent {

    private List<Player> winners = new ArrayList<>();
    private String winMsg = "";
    private List<String> holoInfo = new ArrayList<>();
    private List<String> holoTop = new ArrayList<>();
    private String TopValue = "";
    private String TopValueSuffix = "";
    private String TeamWin = "";
    private Map<Player, String> holoPlayerInfo = new ConcurrentHashMap<>();

    public String getWinMsg() {
        return winMsg;
    }

    public List<Player> getWinners() {
        return winners;
    }

    public List<String> getHoloInfo() {
        return holoInfo;
    }

    public String getTeamWin() {
        return TeamWin;
    }

    public void setTeamWin(String teamWin) {
        TeamWin = teamWin;
    }

    public List<String> getHoloTop() {
        return holoTop;
    }

    public String getTopValue() {
        return TopValue;
    }

    public String getTopValueSuffix() {
        return TopValueSuffix;
    }

    public Map<Player, String> getHoloPlayerInfo() {
        return holoPlayerInfo;
    }

    public void addWinner(Player winner) {
        this.winners.add(winner);
    }

    public void setHoloInfo(List<String> holoInfo) {
        this.holoInfo = holoInfo;
    }

    public void setWinMsg(String winMsg) {
        this.winMsg = winMsg;
    }

    public void setHoloTop(List<String> holoTop) {
        this.holoTop = holoTop;
    }

    public void setTopValue(String topValue) {
        TopValue = topValue;
    }

    public void setTopValueSuffix(String topValueSuffix) {
        TopValueSuffix = topValueSuffix;
    }
}
