package behaviours.assign.trust;

import agents.TrustAgent;
import behaviours.assign.AssignJobsDecision;
import behaviours.assign.AssignJobsPhase;
import behaviours.assign.ResolveConflict;
import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;

public class AssignJobsPhaseTrust extends AssignJobsPhase {
    private final TrustAgent trustAgent;

    public AssignJobsPhaseTrust(TrustAgent trustAgent) {
        super(trustAgent);
        this.trustAgent = trustAgent;
    }

    @Override
    protected ResolveConflict getExternalConflictBehaviour(ExternalConflict conflict) {
        return new ResolveConflictTrust(trustAgent, game, conflict);
    }

    @Override
    protected ResolveConflict getInternalConflictBehaviour(InternalConflict conflict) {
        return new ResolveConflictTrust(trustAgent, game, conflict);
    }

    @Override
    protected AssignJobsDecision getAssignJobsBehaviour() {
        return new AssignJobsDecisionTrust(trustAgent, game);
    }
}
