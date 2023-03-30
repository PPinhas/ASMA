package behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class TurnMaster extends Behaviour {
    private boolean done = false;
    private int turn;

    // Constructor
    public TurnMaster(int turn) {
        this.turn = turn;
    }

    // This method is called when the behavior starts
    public void onStart() {
        System.out.println("Starting TurnMaster");
    }

    // This method is called repeatedly until the behavior is finished
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        String aid = "player" + this.turn + "@172.17.0.1;1099/JADE";
        msg.addReceiver(new AID(aid, AID.ISLOCALNAME));
        msg.setContent("It's your turn");
        myAgent.send(msg);
        // if got response
        done = true;
    }

    // This method is called when the behavior is finished
    public int onEnd() {
        System.out.println("Ending TurnMaster");
        return 0;
    }

    // This method is called to determine whether the behavior is finished
    public boolean done() {
        return done; // This behavior never finishes
    }
}
