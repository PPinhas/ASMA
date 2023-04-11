package game;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

import static config.GameConfig.*;


public class Player implements Comparable<Player> {
    private final int id;
    private int money;
    private final ArrayList<Piece> pieces;
    private final Palace palace;

    public Player(AgentContainer container, int id, Game game, boolean createAgents) throws StaleProxyException {
        this.id = id;
        this.money = STARTING_MONEY;
        this.pieces = new ArrayList<>();
        this.palace = new Palace();

        for (int i = 0; i < NUM_SCRIBES_PER_PLAYER; i++) {
            pieces.add(new Piece(Piece.Job.Scribe, this));
        }
        for (int i = 0; i < NUM_MINISTERS_PER_PLAYER; i++) {
            pieces.add(new Piece(Piece.Job.Minister, this));
        }
        for (int i = 0; i < NUM_ALCHEMISTS_PER_PLAYER; i++) {
            pieces.add(new Piece(Piece.Job.Alchemist, this));
        }
        for (int i = 0; i < NUM_HEALERS_PER_PLAYER; i++) {
            pieces.add(new Piece(Piece.Job.Healer, this));
        }

        if (!createAgents) {
            return;
        }

        String agentName = "player" + this.id;
        Object[] args = new Object[2];
        args[0] = game;
        args[1] = this.id;

        // TODO Initialize right agent
        try {
            AgentController agent = container.createNewAgent(agentName, "agents.RandomAgent", args);
            agent.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    public void moneyTransaction(int amount) {
        this.money += amount;
    }

    public int getMoney() {
        return money;
    }

    public Palace getPalace() {
        return palace;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(this.id, o.id);
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
}
