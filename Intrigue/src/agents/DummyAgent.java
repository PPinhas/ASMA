package agents;

import behaviours.assign.AssignJobsPhase;
import behaviours.bribe.GiveBribe;
import behaviours.seek.SeekJobs;

// TODO Delete later
public class DummyAgent extends IntrigueAgent {
    @Override
    protected AssignJobsPhase getAssignJobsBehaviour() {
        return null;
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return null;
    }

    @Override
    protected GiveBribe getResolveConflictBehaviour() {
        return null;
    }
}
