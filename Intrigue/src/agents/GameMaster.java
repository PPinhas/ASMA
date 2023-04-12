package agents;

import behaviours.GameUpdateListener;
import config.GameConfig;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.List;

import static behaviours.BehaviourUtils.buildMessage;
import static config.Protocols.*;

public class GameMaster extends InformedAgent {
    protected void setup() {
        super.setup();
        game.display();
    }

    private class GameMasterUpdateListener extends GameUpdateListener {
        public GameMasterUpdateListener(InformedAgent agent) {
            super(agent);
        }

        public void onStart() {
            super.onStart();

            block(GameConfig.ACTION_DELAY_MS);
            AID receiver = getAgentByPlayerId(game.getCurrentPlayerId());
            ACLMessage seekMsg = buildMessage(ACLMessage.REQUEST, SEEK_EMPLOYMENT, List.of(receiver));
            send(seekMsg);
        }

        @Override
        protected void handleJobsAssigned(ACLMessage msg) {
            super.handleJobsAssigned(msg);

            if (!game.getCurrentPlayer().getPalace().getParkPieces().isEmpty()) { // not over yet
                return;
            }

            block(GameConfig.ACTION_DELAY_MS);
            AID receiver = getAgentByPlayerId(game.getCurrentPlayerId());
            ACLMessage seekMsg = buildMessage(ACLMessage.REQUEST, SEEK_EMPLOYMENT, List.of(receiver));
            send(seekMsg);
        }

        @Override
        protected void handleEmployeesSent(ACLMessage msg) {
            super.handleEmployeesSent(msg);
            block(GameConfig.ACTION_DELAY_MS);
            ACLMessage newTurnMsg = buildMessage(ACLMessage.INFORM, NEW_TURN, getAgents());
            send(newTurnMsg);
        }

        @Override
        protected void handleBribeOffered(ACLMessage msg) {
            super.handleBribeOffered(msg);
        }

        @Override
        protected void handleNewTurn(ACLMessage msg) {
            super.handleNewTurn(msg);
            game.display();
            if (game.isOver()) {
                game.endGame();
                return; // TODO SHUT DOWN
            }

            block(GameConfig.ACTION_DELAY_MS);
            ACLMessage newTurnMsg = buildMessage(ACLMessage.INFORM, COLLECT_INCOME, getAgents());
            send(newTurnMsg);
        }

        @Override
        protected void handleCollectIncome(ACLMessage msg) {
            super.handleCollectIncome(msg);

            block(GameConfig.ACTION_DELAY_MS);
            AID receiver = getAgentByPlayerId(game.getCurrentPlayerId());
            ACLMessage seekMsg = buildMessage(ACLMessage.REQUEST, ASSIGN_JOBS, List.of(receiver));
            send(seekMsg);
        }
    }

    @Override
    protected GameUpdateListener getGameUpdateListener() {
        return new GameMasterUpdateListener(this);
    }
}
