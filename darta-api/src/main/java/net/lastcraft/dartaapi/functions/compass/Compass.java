package net.lastcraft.dartaapi.functions.compass;

import net.lastcraft.base.util.DList;
import net.lastcraft.dartaapi.utils.core.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.Collection;

@Deprecated
public class Compass {

    private Player player;
    private DList<Player> players;
    private Player currentPlayer;

    public Compass(Player player){
        this.player = player;
        Collection<Player> alive = PlayerUtil.getAlivePlayers();
        alive.remove(player);
        players = new DList<>(alive);
        setNewPlayer();
    }

    public Player getPlayer(){
        return player;
    }

    public Player getCurrentPlayer(){
        if (!PlayerUtil.isAlive(currentPlayer)){
            setNewPlayer();
        }
        return currentPlayer;
    }

    public void setNewPlayer() {
        if (players.isEmpty()){
            currentPlayer = null;
        } else {
            for (int i = 0; i<players.size(); i++){
                Player player = players.getNext();
                if (PlayerUtil.isAlive(player)){
                    currentPlayer = player;
                    return;
                }
            }
            currentPlayer = null;
        }
    }

    static {
        new CompassListener();
        new CompassTask();
    }
}
