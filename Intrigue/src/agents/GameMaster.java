package agents;

import behaviours.TurnMaster;
import jade.core.behaviours.Behaviour;

public class GameMaster extends InformedAgent {
    protected void setup() {
        super.setup();

        while (!game.isOver()) {
            System.out.println("Starting turn with player " + (game.getCurrentPlayerId() + 1));
            Behaviour turnMaster = new TurnMaster(game.getCurrentPlayerId() + 1);
            addBehaviour(turnMaster);
            while (!turnMaster.done()) {
                //do nothing
            }
            game.nextTurn();
            System.out.println("Turn is over.");
        }
    }
}
