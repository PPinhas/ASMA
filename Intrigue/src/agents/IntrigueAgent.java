package agents;

import behaviours.WaitForAction;
import behaviours.assign.AssignJobs;
import behaviours.bribe.ResolveConflict;
import behaviours.seek.SeekJobs;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;

import static config.Messages.*;

/**
 * Abstract class for all Intrigue agents. All subclasses should only declare the behaviours they want to use.
 */
public abstract class IntrigueAgent extends Agent {
    private int id;

    protected void setup() {
        System.out.println("Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        this.id = (int) args[0];

        CyclicBehaviour waitForAction = new WaitForAction();
        addBehaviour(waitForAction);
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }

    public void handleAction(String action) {
        switch (action) {
            case ASSIGN_MESSAGE -> addBehaviour(getAssignJobsBehaviour());
            case SEEK_MESSAGE -> addBehaviour(getSeekJobsBehaviour());
            case RESOLVE_MESSAGE -> addBehaviour(getResolveConflictBehaviour());
        }
    }

    protected abstract AssignJobs getAssignJobsBehaviour();

    protected abstract SeekJobs getSeekJobsBehaviour();

    protected abstract ResolveConflict getResolveConflictBehaviour();
}
