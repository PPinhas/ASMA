package agents;
import game.Game;
import game.Palace;
import game.Piece;
import jade.core.Agent;

import java.util.ArrayList;

public class IntrigeAgent extends Agent {
    private int id;
    public IntrigeAgent() {
        super();
    }

    protected void setup() {
        System.out.println("");
        getAID().setName("IntrigeAgent" + this.id);
        System.out.println("Agent " + getAID().getName() + " is ready.");
    }
    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }

}
