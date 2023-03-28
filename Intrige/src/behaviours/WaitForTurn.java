package behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class WaitForTurn extends CyclicBehaviour {
    public void action() {
        // receive a message
        ACLMessage msg = myAgent.receive();

        if (msg != null) {
            // handle the message
            String content = msg.getContent();
            System.out.println("Received message: " + content);

            // reply to the message
            /*
            ACLMessage reply = msg.createReply();
            reply.setContent("Got it!");
            myAgent.send(reply);
            */

        } else {
            // block the behavior until a message is received
            block();
        }
    }
}