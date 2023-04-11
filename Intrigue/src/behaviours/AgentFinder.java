package behaviours;

import agents.InformedAgent;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

import java.util.ArrayList;

public class AgentFinder extends SubscriptionInitiator {
    private final ArrayList<AID> agents;

    public AgentFinder(InformedAgent informedAgent, DFAgentDescription dfad) {
        super(informedAgent, DFService.createSubscriptionMessage(informedAgent, informedAgent.getDefaultDF(), dfad, null));
        this.agents = informedAgent.getAgents();

        try {
            DFAgentDescription[] result = DFService.search(informedAgent, dfad);
            for (DFAgentDescription dfd : result) {
                this.addAgent(dfd);
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    protected void handleInform(ACLMessage inform) {
        try {
            DFAgentDescription[] newAgents = DFService.decodeNotification(inform.getContent());
            for (DFAgentDescription dfd : newAgents) {
                this.addAgent(dfd);
            }
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    private boolean containsAgent(AID agent) {
        for (AID aid : agents) {
            if (aid.getName().equals(agent.getName())) {
                return true;
            }
        }
        return false;
    }

    private void addAgent(DFAgentDescription dfd) {
        AID agent = dfd.getName();
        // It also adds itself to simplify game updates and avoid code duplication
        if (!this.containsAgent(agent)) {
            //System.out.println("New agent in town: " + agent.getLocalName() + " (from " + this.myAgent.getLocalName() + ")");
            this.agents.add(agent);
        }
    }
}
