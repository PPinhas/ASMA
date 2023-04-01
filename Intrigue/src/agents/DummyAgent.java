package agents;

import behaviours.assign.AssignJobs;
import behaviours.bribe.ResolveConflict;
import behaviours.seek.SeekJobs;

// TODO Delete later
public class DummyAgent extends IntrigueAgent {
    @Override
    protected AssignJobs getAssignJobsBehaviour() {
        return null;
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return null;
    }

    @Override
    protected ResolveConflict getResolveConflictBehaviour() {
        return null;
    }
}
