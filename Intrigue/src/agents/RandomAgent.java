package agents;

import behaviours.assign.AssignJobsPhase;
import behaviours.assign.random.AssignJobsPhaseRandom;
import behaviours.bribe.GiveBribe;
import behaviours.bribe.GiveBribeRandom;
import behaviours.seek.SeekJobs;
import behaviours.seek.SeekJobsRandom;
import config.messages.ResolveConflict;

public class RandomAgent extends IntrigueAgent {
    @Override
    protected AssignJobsPhase getAssignJobsBehaviour() {
        return new AssignJobsPhaseRandom(this);
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return new SeekJobsRandom(this);
    }

    @Override
    protected GiveBribe getResolveConflictBehaviour(ResolveConflict conflict) {
        return new GiveBribeRandom(this, conflict);
    }
}
