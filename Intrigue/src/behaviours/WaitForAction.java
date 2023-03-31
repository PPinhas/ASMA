package behaviours;

import agents.IntrigueAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForAction extends CyclicBehaviour {
    public void action() {
        if (!(myAgent instanceof IntrigueAgent intrigueAgent)) {
            throw new RuntimeException("WaitForAction can only be used by IntrigueAgent");
        }

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
