package behaviours;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class BehaviourUtils {
    public static MessageTemplate buildMessageTemplate(int performative, String... protocols) {
        MessageTemplate template = MessageTemplate.MatchProtocol(protocols[0]);
        for (int i = 1; i < protocols.length; i++) {
            template = MessageTemplate.or(template, MessageTemplate.MatchProtocol(protocols[i]));
        }
        return MessageTemplate.and(MessageTemplate.MatchPerformative(performative), template);
    }

    public static ACLMessage buildMessage(int performative, String protocol, Serializable content, List<AID> receivers) {
        ACLMessage msg = new ACLMessage(performative);
        msg.setProtocol(protocol);
        if (content != null) {
            try {
                msg.setContentObject(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        for (AID receiver : receivers) {
            msg.addReceiver(receiver);
        }
        return msg;
    }

    public static ACLMessage buildMessage(int performative, String protocol, List<AID> receivers) {
        return buildMessage(performative, protocol, null, receivers);
    }
}
