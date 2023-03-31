package agents;

import behaviours.GameUpdateListener;
import behaviours.WaitForAction;
import behaviours.assign.AssignJobs;
import behaviours.bribe.ResolveConflict;
import behaviours.seek.SeekJobs;
import game.Game;
import jade.core.Agent;

import static config.Protocols.*;

/**
 * Abstract class for all Intrigue agents. All subclasses should only declare the behaviours they want to use.
 */
public abstract class IntrigueAgent extends Agent {
    protected int id;
    protected Game game;

    protected void setup() {
        System.out.println("Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        this.id = (int) args[0];
        this.game = (Game) args[1];

        addBehaviour(new WaitForAction(this));
        addBehaviour(new GameUpdateListener(this));
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }

    public void handleAction(String action) {
        switch (action) {
            case ASSIGN_JOBS -> addBehaviour(getAssignJobsBehaviour());
            case SEEK_EMPLOYMENT -> addBehaviour(getSeekJobsBehaviour());
            case RESOLVE_CONFLICT -> addBehaviour(getResolveConflictBehaviour());
        }
    }

    protected abstract AssignJobs getAssignJobsBehaviour();

    protected abstract SeekJobs getSeekJobsBehaviour();

    protected abstract ResolveConflict getResolveConflictBehaviour();

    public int getId() {
        return id;
    }

    public Game getGame() {
        return game;
    }
}
