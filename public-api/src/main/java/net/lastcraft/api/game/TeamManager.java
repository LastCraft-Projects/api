package net.lastcraft.api.game;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Deprecated
@Getter
public class TeamManager {

    private String team;
    private String name;
    private String shortName;
    private ChatColor chatColor;
    private Color color;
    private short subID;

    public static LinkedHashMap<String, TeamManager> TEAMS = new LinkedHashMap<>();

    public static LinkedHashMap<String, TeamManager> getTeams(){
        return TEAMS;
    }

    public TeamManager(){
        for (int i = 0; i < GameSettings.numberOfTeams; i++){
            switch (i) {
                case 0:
                    new TeamManager("Red", "Красные", "A", ChatColor.RED, Color.fromRGB(255, 0, 0), (short) 14);
                    break;
                case 1:
                    new TeamManager("DarkBlue", "Синие", "B", ChatColor.BLUE, Color.fromRGB(0, 77, 255), (short) 11);
                    break;
                case 2:
                    new TeamManager("Green", "Зеленые", "C", ChatColor.DARK_GREEN, Color.fromRGB(51,102,0), (short) 13);
                    break;
                case 3:
                    new TeamManager("Yellow", "Желтые", "D", ChatColor.YELLOW, Color.fromRGB(255, 255, 0), (short) 4);
                    break;
                case 4:
                    new TeamManager("Turquoise", "Бирюзовые", "E", ChatColor.DARK_AQUA, Color.fromRGB(48, 213, 200), (short) 9);
                    break;
                case 5:
                    new TeamManager("White", "Белые", "F", ChatColor.WHITE, Color.fromRGB(255, 255, 255), (short) 0);
                    break;
                case 6:
                    new TeamManager("Orange", "Оранжевые", "G", ChatColor.GOLD, Color.fromRGB(255, 128, 0), (short) 1);
                    break;
                case 7:
                    new TeamManager("LightGreen", "Салатовые", "H", ChatColor.GREEN, Color.fromRGB(80,255,0), (short) 5);
                    break;
                case 8:
                    new TeamManager("Pink", "Розовые", "I", ChatColor.LIGHT_PURPLE, Color.fromRGB(255, 203, 219), (short) 6);
                    break;
                case 9:
                    new TeamManager("Blue", "Голубые", "J", ChatColor.AQUA, Color.fromRGB(0, 191, 255), (short) 3);
                    break;
                case 10:
                    new TeamManager("Gray", "Серые", "K", ChatColor.GRAY, Color.fromRGB(133, 133, 133), (short) 8);
                    break;
                case 11:
                    new TeamManager("Purple", "Фиолетовые", "L", ChatColor.DARK_PURPLE, Color.fromRGB(90, 0, 157), (short) 10);
                    break;
            }
        }
    }

    private TeamManager(String team, String name, String shortName, ChatColor chatColor, Color color, short subID){
        this.team = team;
        this.name = name;
        this.shortName = shortName;
        this.chatColor = chatColor;
        this.color = color;
        this.subID = subID;

        TEAMS.put(team, this);
    }

    public static int[] getSlotsTeams(){
        HashMap<Integer, int[]> slotsTeams = new HashMap<>();
        slotsTeams.put(1, new int[]{27, 13});
        slotsTeams.put(2, new int[]{27, 11, 15});
        slotsTeams.put(3, new int[]{27, 10, 13, 16});
        slotsTeams.put(4, new int[]{27, 10, 12, 14, 16});
        slotsTeams.put(5, new int[]{45, 10, 13, 16, 29, 33});
        slotsTeams.put(6, new int[]{45, 10, 13, 16, 28, 31, 34});
        slotsTeams.put(7, new int[]{45, 10, 12, 14, 16, 29, 31, 33});
        slotsTeams.put(8, new int[]{45, 10, 12, 14, 16, 28, 30, 32, 34});
        slotsTeams.put(9, new int[]{45, 10, 12, 14, 16, 22, 28, 30, 32, 34});
        slotsTeams.put(10, new int[]{45, 10, 12, 14, 16, 20, 24, 28, 30, 32, 34});
        slotsTeams.put(11, new int[]{45, 10, 12, 14, 16, 20, 22, 24, 28, 30, 32, 34});
        slotsTeams.put(12, new int[]{54, 10, 12, 14, 16, 20, 24, 28, 30, 32, 34, 38, 42});

        return slotsTeams.get(GameSettings.numberOfTeams);
    }
}
