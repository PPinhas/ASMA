package behaviours.seek;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.EmployeesSent;
import game.Game;
import game.Player;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public abstract class SeekJobs extends OneShotBehaviour {

    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    public SeekJobs(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
        this.game = intrigueAgent.getGame();
    }

    public void action() {
        Player me = intrigueAgent.getOwnPlayer();
        EmployeesSent employeesSent = seekJobs();
        if (employeesSent.pieceIndices().isEmpty()) return; // Probably not possible

        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.EMPLOYEES_SENT, employeesSent, intrigueAgent.getAgents());
        intrigueAgent.send(msg);
    }

    public abstract EmployeesSent seekJobs();
}
