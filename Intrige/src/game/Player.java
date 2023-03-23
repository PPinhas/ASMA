package game;

import agents.IntrigeAgent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import static game.Piece.Job.*;


public class Player {
    private ArrayList<Bill> money;
    private ArrayList<Piece> pieces;

    public Player(AgentContainer container, int id) throws StaleProxyException {
        this.money = new ArrayList<Bill>();
        this.pieces = new ArrayList<Piece>();
        for(int i = 0; i < 2; i++){
            this.money.add(new Bill(1000));
            this.money.add(new Bill(5000));
            this.money.add(new Bill(10000));

            this.pieces.add(new Piece(Scribe));
            this.pieces.add(new Piece(Minister));
            this.pieces.add(new Piece(Alchemist));
            this.pieces.add(new Piece(Healer));

        }
        String agentName = "player" + id;
        AgentController agent = container.createNewAgent(agentName, "agents.IntrigeAgent", null);
        agent.start();
    }

    public void playTurn() {
    }
}
