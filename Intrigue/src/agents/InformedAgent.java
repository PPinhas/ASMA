package agents;

import behaviours.AgentFinder;
import behaviours.GameUpdateListener;
import game.Game;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;

public abstract class InformedAgent extends Agent {
    protected Game game;
    protected final ArrayList<AID> agents = new ArrayList<>();

    protected void setup() {
        System.out.println("Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        this.game = (Game) args[0];

        DFAgentDescription dfd = this.registerAgent();
        addBehaviour(new AgentFinder(this, dfd));
        addBehaviour(new GameUpdateListener(this));
    }

    private DFAgentDescription registerAgent() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType("intrigue-updates");
        sd.setName(getLocalName());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        return dfd;
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }

    public Game getGame() {
        return game;
    }

    public ArrayList<AID> getAgents() {
        return agents;
    }
}
