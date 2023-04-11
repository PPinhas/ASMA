package agents;

import behaviours.WaitForAction;
import behaviours.assign.AssignJobsPhase;
import behaviours.bribe.GiveBribe;
import behaviours.seek.SeekJobs;
import config.messages.ResolveConflict;
import game.Player;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

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
        game.start();

        addBehaviour(new WaitForAction(this));
    }

    public void handleAction(String action, ACLMessage msg) {
        switch (action) {
            case ASSIGN_JOBS -> addBehaviour(getAssignJobsBehaviour());
            case SEEK_EMPLOYMENT -> addBehaviour(getSeekJobsBehaviour());
            case RESOLVE_CONFLICT -> {
                try {
                    addBehaviour(getResolveConflictBehaviour((ResolveConflict) msg.getContentObject()));
                } catch (UnreadableException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected abstract AssignJobsPhase getAssignJobsBehaviour();

    protected abstract SeekJobs getSeekJobsBehaviour();

    protected abstract GiveBribe getResolveConflictBehaviour(ResolveConflict conflict);

    public int getId() {
        return id;
    }

    public Player getOwnPlayer() {
        return game.getPlayerById(id);
    }
}
