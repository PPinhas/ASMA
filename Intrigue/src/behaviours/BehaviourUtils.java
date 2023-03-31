package behaviours;

import jade.lang.acl.MessageTemplate;

public class BehaviourUtils {
    public static MessageTemplate buildMessageTemplate(int performative, String... protocols) {
        MessageTemplate template = MessageTemplate.MatchProtocol(protocols[0]);
        for (int i = 1; i < protocols.length; i++) {
            template = MessageTemplate.or(template, MessageTemplate.MatchProtocol(protocols[i]));
        }
        return MessageTemplate.and(MessageTemplate.MatchPerformative(performative), template);
    }
}
