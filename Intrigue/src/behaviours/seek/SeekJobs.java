package behaviours.seek;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.EmployeesSent;
import game.Game;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public abstract class SeekJobs extends OneShotBehaviour {

    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    protected final ArrayList<Integer> pieceIndices = new ArrayList<>();
    protected final ArrayList<Integer> playerIndices = new ArrayList<>();

    public SeekJobs(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
        this.game = intrigueAgent.getGame();
    }

    public void action() {
        EmployeesSent employeesSent = seekJobs();
        if (employeesSent.pieceIndices().isEmpty()) return; // Probably not possible

        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.EMPLOYEES_SENT, employeesSent, intrigueAgent.getAgents());
        intrigueAgent.send(msg);
    }

    public abstract EmployeesSent seekJobs();
}
