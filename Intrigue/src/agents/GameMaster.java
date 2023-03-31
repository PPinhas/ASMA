package agents;

import behaviours.TurnMaster;
import game.Game;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class GameMaster extends Agent {

    protected void setup() {
        Object[] args = getArguments();
        Game game = (Game) args[0];
        System.out.println(getAID().getName() + " is ready.");

        while (!game.isOver()) {
            System.out.println("Starting turn with player " + (game.getCurrentPlayerIdx() + 1));
            Behaviour turnMaster = new TurnMaster(game.getCurrentPlayerIdx() + 1);
            addBehaviour(turnMaster);
            while (!turnMaster.done()) {
                //do nothing
            }
            game.nextTurn();
            System.out.println("Turn is over.");
        }
    }

    protected void takeDown() {
        System.out.println(getAID().getName() + " is shutting down.");
    }

}
