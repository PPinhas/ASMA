package behaviours.assign;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.GameConfig;
import config.Protocols;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

import static behaviours.BehaviourUtils.buildMessage;
import static config.Protocols.SEEK_EMPLOYMENT;

public abstract class AssignJobsDecision extends OneShotBehaviour {
    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    protected final ArrayList<Integer> pieceIndices = new ArrayList<>();
    protected final ArrayList<Integer> cardIndices = new ArrayList<>();
    protected final ArrayList<Integer> pieceOwners = new ArrayList<>();

    public AssignJobsDecision(IntrigueAgent intrigueAgent, Game game) {
        super(intrigueAgent);
        this.game = game;
        this.intrigueAgent = intrigueAgent;
    }

    public void action() {
        block(GameConfig.ACTION_DELAY_MS); // wait for previous phase to finish
        Palace palace = intrigueAgent.getOwnPlayer().getPalace();
        if (!palace.getParkPieces().isEmpty()) {
            JobsAssigned jobsAssigned = assignJobs(palace);

            block(GameConfig.ACTION_DELAY_MS);
            ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.JOBS_ASSIGNED, jobsAssigned, intrigueAgent.getAgents());
            intrigueAgent.send(msg);
        }

        block(GameConfig.ACTION_DELAY_MS);
        ACLMessage seekMsg = buildMessage(ACLMessage.REQUEST, SEEK_EMPLOYMENT, List.of(intrigueAgent.getAID()));
        intrigueAgent.send(seekMsg);
    }

    protected abstract JobsAssigned assignJobs(Palace palace);
}
