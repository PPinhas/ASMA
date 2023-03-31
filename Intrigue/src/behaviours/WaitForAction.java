package behaviours;

import agents.IntrigueAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForAction extends CyclicBehaviour {
    private final IntrigueAgent intrigueAgent;

    public WaitForAction(IntrigueAgent intrigueAgent) {
        this.intrigueAgent = intrigueAgent;
    }

    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            // TODO how to get game state and reply to master from new behaviour
            String content = msg.getContent();
            intrigueAgent.handleAction(content);
        } else {
            // block the behavior until a message is received
            block();
        }
    }
}
