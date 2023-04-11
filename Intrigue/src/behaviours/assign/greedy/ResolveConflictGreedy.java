package behaviours.assign.greedy;

import agents.IntrigueAgent;
import behaviours.assign.ResolveConflict;
import config.messages.JobsAssigned;
import game.Game;
import game.Player;
import game.conflict.Conflict;

import java.util.TreeSet;

public class ResolveConflictGreedy extends ResolveConflict {

    public ResolveConflictGreedy(IntrigueAgent agent, Game game, Conflict conflict) {
        super(agent, game, conflict);
    }

    @Override
    protected JobsAssigned resolveConflict() {
        TreeSet<Player> conflictPlayers = conflict.getPlayers();
        Player chosenPlayer = conflictPlayers.first();
        for (Player player : conflictPlayers) {
            if (conflict.getBribe(player) > conflict.getBribe(chosenPlayer)) {
                chosenPlayer = player;
            }
        }

        return buildJobsAssignedFromChosenPlayer(chosenPlayer);
    }
}
