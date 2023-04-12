package behaviours;

import agents.IntrigueAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForAction extends CyclicBehaviour {
    private final IntrigueAgent intrigueAgent;

    public WaitForAction(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
    }

    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            // TODO do something with content, maybe pass message
            String content = msg.getProtocol();
            intrigueAgent.handleAction(content, msg);
        } else {
            // block the behavior until a message is received
            block();
        }
    }
}
