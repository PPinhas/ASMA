package agents;

import game.Bill;
import game.Piece;
import jade.core.Agent;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

public class IntrigeAgent extends Agent {
    private int id;
    public IntrigeAgent() {
        super();
    }

    protected void setup() {
        getAID().setName("IntrigeAgent" + this.id);
        System.out.println("Agent " + getAID().getName() + " is ready.");
    }
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }

    protected void playTurn(ArrayList<Piece> pieces, ArrayList<Bill> money) {
    }
}
