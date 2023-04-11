package behaviours.assign.random;

import agents.IntrigueAgent;
import behaviours.assign.ResolveConflict;
import config.messages.JobsAssigned;
import game.Game;
import game.Player;
import game.conflict.Conflict;

/**
 * Randomly chooses a player to assign a job to. Never chooses the current job holder in internal conflicts.
 */
public class ResolveConflictRandom extends ResolveConflict {

    public ResolveConflictRandom(IntrigueAgent agent, Game game, Conflict conflict) {
        super(agent, game, conflict);
    }

    @Override
    protected JobsAssigned resolveConflict() {
        Player[] conflictPlayers = conflict.getPlayers().toArray(new Player[0]);
        // Job holder is ignored in internal conflicts.
        Player chosenPlayer = conflictPlayers[(int) (Math.random() * conflictPlayers.length)];
        return buildJobsAssignedFromChosenPlayer(chosenPlayer);
    }
}
