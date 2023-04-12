package behaviours;

import agents.IntrigueAgent;
import config.Protocols;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitForAction extends CyclicBehaviour {
    private final IntrigueAgent intrigueAgent;

    private final MessageTemplate template = BehaviourUtils.buildMessageTemplate(
            ACLMessage.REQUEST,
            Protocols.ASSIGN_JOBS,
            Protocols.SEEK_EMPLOYMENT,
            Protocols.RESOLVE_CONFLICT
    );

    public WaitForAction(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
    }

    public void action() {
        ACLMessage msg = myAgent.receive(template);
        if (msg != null) {
            String content = msg.getProtocol();
            intrigueAgent.handleAction(content, msg);
        } else {
            // block the behavior until a message is received
            block();
        }
    }
}
