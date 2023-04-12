package behaviours.assign.trust;

import agents.TrustAgent;
import behaviours.assign.ResolveConflict;
import config.messages.JobsAssigned;
import game.Game;
import game.Player;
import game.conflict.Conflict;
import game.conflict.InternalConflict;

import java.util.TreeSet;

public class ResolveConflictTrust extends ResolveConflict {

    private final TrustAgent trustAgent;

    public ResolveConflictTrust(TrustAgent trustAgent, Game game, Conflict conflict) {
        super(trustAgent, game, conflict);
        this.trustAgent = trustAgent;
    }

    @Override
    protected JobsAssigned resolveConflict() {
        TreeSet<Player> conflictPlayers = conflict.getPlayers();
        Player chosenPlayer = conflictPlayers.first();
        for (Player player : conflictPlayers) {
            int playerTrust = trustAgent.getTrustFactor(player);
            int currentTrust = trustAgent.getTrustFactor(chosenPlayer);
            if (playerTrust > currentTrust) {
                chosenPlayer = player;
            }
        }

        if (conflict instanceof InternalConflict internalConflict) {
            if (trustAgent.getTrustFactor(internalConflict.getJobHolder()) > trustAgent.getTrustFactor(chosenPlayer)) {
                chosenPlayer = internalConflict.getJobHolder();
            }
        }

        return buildJobsAssignedFromChosenPlayer(chosenPlayer);
    }
}
