package agents;

import behaviours.assign.AssignJobsPhase;
import behaviours.assign.random.AssignJobsPhaseRandom;
import behaviours.bribe.GiveBribe;
import behaviours.bribe.GiveRandomBribe;
import behaviours.seek.RandomSeekJobs;
import behaviours.seek.SeekJobs;

public class RandomAgent extends IntrigueAgent {
    @Override
    protected AssignJobsPhase getAssignJobsBehaviour() {
        return new AssignJobsPhaseRandom(this);
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return new RandomSeekJobs(this);
    }

    @Override
    protected GiveBribe getResolveConflictBehaviour() {
        return new GiveRandomBribe(this);
    }
}
