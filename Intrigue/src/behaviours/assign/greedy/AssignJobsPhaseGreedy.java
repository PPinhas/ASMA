package behaviours.assign.greedy;

import agents.IntrigueAgent;
import behaviours.assign.AssignJobsDecision;
import behaviours.assign.AssignJobsPhase;
import behaviours.assign.ResolveConflict;
import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;

public class AssignJobsPhaseGreedy extends AssignJobsPhase {
    public AssignJobsPhaseGreedy(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    protected ResolveConflict getExternalConflictBehaviour(ExternalConflict conflict) {
        return new ResolveConflictGreedy(intrigueAgent, game, conflict);
    }

    @Override
    protected ResolveConflict getInternalConflictBehaviour(InternalConflict conflict) {
        return new ResolveConflictGreedy(intrigueAgent, game, conflict);
    }

    @Override
    protected AssignJobsDecision getAssignJobsBehaviour() {
        return new AssignJobsDecisionGreedy(intrigueAgent, game);
    }
}
