package agents;

import behaviours.PlayTurn;
import behaviours.WaitForTurn;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;

public class IntrigeAgent extends Agent {
    private int id;

    public IntrigeAgent() {
        super();
    }

    protected void setup() {
        System.out.println("Agent " + getAID().getName() + " is ready.");
        Object[] args = getArguments();
        id = (int) args[0];
        CyclicBehaviour waitForTurn = new WaitForTurn();
        addBehaviour(waitForTurn);
        while (!waitForTurn.done()) {
            //do nothing
        }
        Behaviour playTurn = new PlayTurn();
        addBehaviour(playTurn);
    }

    protected void takeDown() {
        System.out.println("Agent " + getAID().getName() + " is shutting down.");
    }
}
