package behaviours.assign.random;

import agents.IntrigueAgent;
import behaviours.assign.AssignJobsDecision;
import behaviours.assign.AssignJobsPhase;
import behaviours.assign.ResolveConflict;
import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;

public class AssignJobsPhaseRandom extends AssignJobsPhase {
    public AssignJobsPhaseRandom(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    protected ResolveConflict getExternalConflictBehaviour(ExternalConflict conflict) {
        return new ResolveConflictRandom(intrigueAgent, game, conflict);
    }

    @Override
    protected ResolveConflict getInternalConflictBehaviour(InternalConflict conflict) {
        return new ResolveConflictRandom(intrigueAgent, game, conflict);
    }

    @Override
    protected AssignJobsDecision getAssignJobsBehaviour() {
        return new AssignJobsDecisionRandom(intrigueAgent, game);
    }
}
