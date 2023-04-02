package behaviours.assign;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public abstract class AssignJobsDecision extends OneShotBehaviour {
    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    public AssignJobsDecision(IntrigueAgent intrigueAgent, Game game) {
        super(intrigueAgent);
        this.game = game;
        this.intrigueAgent = intrigueAgent;
    }

    public void action() {
        Palace palace = game.getCurrentPlayer().getPalace();
        JobsAssigned jobsAssigned = assignJobs(palace);
        if (jobsAssigned.selectedPieceIndices().isEmpty()) return;

        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.JOBS_ASSIGNED, jobsAssigned, intrigueAgent.getAgents());
        intrigueAgent.send(msg);
    }

    protected abstract JobsAssigned assignJobs(Palace palace);
}
