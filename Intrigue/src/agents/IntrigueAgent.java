package agents;

import behaviours.WaitForAction;
import behaviours.assign.AssignJobsPhase;
import behaviours.bribe.GiveBribe;
import behaviours.seek.SeekJobs;
import game.Player;

import static config.Protocols.*;

/**
 * Abstract class for all Intrigue agents. All subclasses should only declare the behaviours they want to use.
 */
public abstract class IntrigueAgent extends InformedAgent {
    protected int id;

    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        this.id = (int) args[1];

        addBehaviour(new WaitForAction(this));
    }

    public void handleAction(String action) {
        switch (action) {
            case ASSIGN_JOBS -> addBehaviour(getAssignJobsBehaviour());
            case SEEK_EMPLOYMENT -> addBehaviour(getSeekJobsBehaviour());
            case RESOLVE_CONFLICT -> addBehaviour(getResolveConflictBehaviour());
        }
    }

    protected abstract AssignJobsPhase getAssignJobsBehaviour();

    protected abstract SeekJobs getSeekJobsBehaviour();

    protected abstract GiveBribe getResolveConflictBehaviour();

    public int getId() {
        return id;
    }

    public Player getOwnPlayer() {
        return game.getPlayerById(id);
    }
}