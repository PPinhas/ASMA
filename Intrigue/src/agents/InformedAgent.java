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

        this.registerAgent();
        this.subscribeToFindAgents();
        addBehaviour(getGameUpdateListener());
    }

    private void registerAgent() {
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
    }

    private void subscribeToFindAgents() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("intrigue-updates");
        dfd.addServices(sd);
        addBehaviour(new AgentFinder(this, dfd));
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

    public AID getAgentByPlayerId(int playerId) {
        for (AID agent : agents) {
            if (agent.getLocalName().equals("player" + playerId)) {
                return agent;
            }
        }
        return null;
    }

    public AID getGameMasterAgent() {
        for (AID agent : agents) {
            if (agent.getLocalName().equals("GameMaster")) {
                return agent;
            }
        }
        return null;
    }

    protected GameUpdateListener getGameUpdateListener() {
        return new GameUpdateListener(this);
    }
}
