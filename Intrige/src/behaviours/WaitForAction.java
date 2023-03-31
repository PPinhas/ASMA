package behaviours;

import agents.IntrigeAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForAction extends CyclicBehaviour {
    public void action() {
        if (!(myAgent instanceof IntrigeAgent intrigeAgent)) {
            throw new RuntimeException("WaitForAction can only be used by IntrigeAgent");
        }

        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            // TODO how to get game state and reply to master from new behaviour
            String content = msg.getContent();
            intrigeAgent.handleAction(content);
        } else {
            // block the behavior until a message is received
            block();
        }
    }
}
