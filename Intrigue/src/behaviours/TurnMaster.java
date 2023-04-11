package behaviours;

import behaviours.assign.ResolveConflict;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import static config.GameConfig.TIMEOUT;
import static config.Protocols.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class TurnMaster extends Behaviour {
    private boolean done = false;
    private int turn;

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


        //ArrayList<String> protocols = new ArrayList<>(Arrays.asList(RESOLVE_CONFLICT, ASSIGN_JOBS, SEEK_EMPLOYMENT));
        ArrayList<String> protocols = new ArrayList<>(Arrays.asList(SEEK_EMPLOYMENT));


        for(String protocol : protocols){
            msg.setProtocol(protocol);
            myAgent.send(msg);
            ACLMessage response = myAgent.blockingReceive(TIMEOUT);
            if(response != null) {
                System.out.println("TurnMaster: response for protocol "+ protocol + " from player "+ this.turn + "is: " + response.getContent());
                break;
            } else {
                System.out.println("TurnMaster: No response for protocol " + protocol + " from player " + this.turn);
            }
        }
        done = true;
    }

    public boolean done() {
        return done; // This behavior never finishes
    }
}
