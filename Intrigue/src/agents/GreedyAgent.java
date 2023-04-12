package agents;

import behaviours.assign.AssignJobsPhase;
import behaviours.assign.greedy.AssignJobsPhaseGreedy;
import behaviours.bribe.GiveBribe;
import behaviours.bribe.GiveBribeGreedy;
import behaviours.seek.SeekJobs;
import behaviours.seek.SeekJobsGreedy;
import config.messages.ResolveConflict;

public class GreedyAgent extends IntrigueAgent {
    @Override
    protected AssignJobsPhase getAssignJobsBehaviour() {
        return new AssignJobsPhaseGreedy(this);
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return new SeekJobsGreedy(this);
    }

    @Override
    protected GiveBribe getResolveConflictBehaviour(ResolveConflict conflict) {
        return new GiveBribeGreedy(this, conflict);
    }
}
