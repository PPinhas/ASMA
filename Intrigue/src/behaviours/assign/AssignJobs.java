package behaviours.assign;

import agents.IntrigueAgent;
import game.Game;
import game.Palace;
import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;
import jade.core.behaviours.Behaviour;

import java.util.ArrayList;
import java.util.TreeSet;

public abstract class AssignJobs extends Behaviour {

    private final Game game;
    private final ArrayList<ExternalConflict> externalConflicts = new ArrayList<>();
    private final TreeSet<InternalConflict> internalConflicts = new TreeSet<>();

    public AssignJobs(IntrigueAgent intrigueAgent) {
        this.game = intrigueAgent.getGame();
    }

    public void onStart() {
        Palace palace = game.getCurrentPlayer().getPalace();
        externalConflicts.addAll(palace.getExternalConflicts());
        internalConflicts.addAll(palace.getInternalConflicts());
    }

    public void action() {
        if (!externalConflicts.isEmpty()) {
            // TODO handle external conflicts
        } else if (!internalConflicts.isEmpty()) {
            // TODO handle internal conflicts
        } else {
            // TODO abstract decision of assigning jobs, broadcast result
        }
    }

    public int onEnd() {
        System.out.println("Ending assign jobs");
        return 0;
    }

    public boolean done() {
        return false; // This behavior never finishes
    }
}
