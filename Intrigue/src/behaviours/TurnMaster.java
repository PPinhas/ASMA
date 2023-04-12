package behaviours;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Arrays;

import static config.GameConfig.TIMEOUT;
import static config.Protocols.NEW_TURN;
import static config.Protocols.SEEK_EMPLOYMENT;

public class TurnMaster extends Behaviour {
    private boolean done = false;
    private final int turn;

    public TurnMaster(int turn, Agent agent) {
        super(agent);
        this.turn = turn;
        this.action();
    }

    public void action() {
        ACLMessage msg = new ACLMessage();
        //String aid = "player" + this.turn + "@172.17.0.1;1099/JADE";
        String aid = "player" + this.turn;
        msg.addReceiver(new AID(aid, AID.ISLOCALNAME));


        //ArrayList<String> protocols = new ArrayList<>(Arrays.asList(RESOLVE_CONFLICT, ASSIGN_JOBS, SEEK_EMPLOYMENT, NEW_TURN));
        ArrayList<String> protocols = new ArrayList<>(Arrays.asList(SEEK_EMPLOYMENT, NEW_TURN));


        for (String protocol : protocols) {
            msg.setProtocol(protocol);
            myAgent.send(msg);
            ACLMessage response = myAgent.blockingReceive(TIMEOUT);
            if (response != null) {
                System.out.println("TurnMaster: response for protocol " + protocol + " from player " + this.turn + "is: " + response.getContent());
                break;
            } else {
                System.out.println("TurnMaster: No response for protocol " + protocol + " from player " + this.turn);
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        done = true;
    }

    public boolean done() {
        return done;
    }
}
