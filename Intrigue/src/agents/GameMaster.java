package agents;

import behaviours.TurnMaster;
import jade.core.behaviours.Behaviour;

public class GameMaster extends InformedAgent {
    protected void setup() {
        super.setup();

        while (!game.isOver()) {
            Behaviour turnMaster = new TurnMaster(game.getCurrentPlayerId(), this);
            addBehaviour(turnMaster);
            while (!turnMaster.done()) {
            }
            //game.nextTurn();
            break;
        }

    }
}
