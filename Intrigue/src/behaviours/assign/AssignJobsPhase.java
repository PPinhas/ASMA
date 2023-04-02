package behaviours.assign;

import agents.IntrigueAgent;
import game.Game;
import game.Palace;
import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;
import jade.core.behaviours.SequentialBehaviour;

import java.util.ArrayList;
import java.util.TreeSet;

public abstract class AssignJobsPhase extends SequentialBehaviour {
    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    public AssignJobsPhase(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
        this.game = intrigueAgent.getGame();
    }

    public void onStart() {
        Palace palace = game.getCurrentPlayer().getPalace();
        ArrayList<ExternalConflict> externalConflicts = palace.getExternalConflicts();
        TreeSet<InternalConflict> internalConflicts = palace.getInternalConflicts();

        for (ExternalConflict conflict : externalConflicts) {
            this.addSubBehaviour(getExternalConflictBehaviour(conflict));
        }
        for (InternalConflict conflict : internalConflicts) {
            this.addSubBehaviour(getInternalConflictBehaviour(conflict));
        }
        this.addSubBehaviour(getAssignJobsBehaviour());
    }

    protected abstract ResolveConflict getExternalConflictBehaviour(ExternalConflict conflict);

    protected abstract ResolveConflict getInternalConflictBehaviour(InternalConflict conflict);

    protected abstract AssignJobsDecision getAssignJobsBehaviour();
}
