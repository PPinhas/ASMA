package game;

import agents.IntrigeAgent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import static game.Piece.Job.*;


public class Player {
    private int money;
    private  AgentController agent;
    private ArrayList<Piece> pieces;

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public Player(AgentContainer container, int id) throws StaleProxyException {
        this.money = 32000;
        this.pieces = new ArrayList<Piece>();
        for(int i = 0; i < 2; i++){
            this.pieces.add(new Piece(Scribe, this));
            this.pieces.add(new Piece(Minister, this));
            this.pieces.add(new Piece(Alchemist, this));
            this.pieces.add(new Piece(Healer, this));

        }
        String agentName = "player" + id;
        Object[] args = new Object[1];
        args[0] = id;

        agent = container.createNewAgent(agentName, "agents.IntrigeAgent", args);
        agent.start();
    }
}
