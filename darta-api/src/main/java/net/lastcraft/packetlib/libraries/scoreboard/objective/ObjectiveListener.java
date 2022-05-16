package net.lastcraft.packetlib.libraries.scoreboard.objective;

import net.lastcraft.api.event.game.EndGameEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerJoinEvent;
import net.lastcraft.api.event.gamer.async.AsyncGamerQuitEvent;
import net.lastcraft.dartaapi.listeners.DListener;
import net.lastcraft.dartaapi.loader.DartaAPI;
import net.lastcraft.packetlib.libraries.scoreboard.ScoreBoardAPIImpl;
import net.lastcraft.packetlib.nms.NmsAPI;
import net.lastcraft.packetlib.nms.interfaces.packet.PacketContainer;
import net.lastcraft.packetlib.nms.scoreboard.DScore;
import net.lastcraft.packetlib.nms.scoreboard.ScoreboardAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class ObjectiveListener extends DListener<DartaAPI> {

    private final PacketContainer packetContainer = NmsAPI.getManager().getPacketContainer();
    private final ObjectiveManager objectiveManager;

    public ObjectiveListener(ScoreBoardAPIImpl scoreBoardAPI) {
        super(scoreBoardAPI.getDartaAPI());
        this.objectiveManager = scoreBoardAPI.getObjectiveManager();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(AsyncGamerJoinEvent e) {
        Player player = e.getGamer().getPlayer();
        if (player == null || !player.isOnline()) {
            return;
        }

        for (CraftObjective craftObjective : objectiveManager.getObjectives()) {
            if (craftObjective.isPublic()) {
                craftObjective.showTo(player);
            }

            for (String name : craftObjective.getScores().keySet()) {
                Player all = Bukkit.getPlayer(name);
                if (all == null || !all.isOnline()) {
                    continue;
                }

                Integer score = craftObjective.getScores().get(name);
                if (score == null) {
                    continue;
                }

                DScore dScore = new DScore(all.getName(), craftObjective.getDObjective(), score);
                packetContainer.getScoreboardScorePacket(dScore, ScoreboardAction.CHANGE).sendPacket(player);
            }
        }
    }

    @EventHandler
    public void onQuit(AsyncGamerQuitEvent e) {
        String name = e.getGamer().getName();

        for (CraftObjective craftObjective : objectiveManager.getObjectives()) {
            if (craftObjective.getOwner() != null && craftObjective.getOwner().getName().equalsIgnoreCase(name)) {
                craftObjective.remove();
                continue;
            }

            craftObjective.removeScore(name);
            craftObjective.removeTo(e.getGamer());
        }
    }

    @EventHandler
    public void onEndGame(EndGameEvent e) {
        objectiveManager.getObjectives().forEach(CraftObjective::remove);
    }
}
