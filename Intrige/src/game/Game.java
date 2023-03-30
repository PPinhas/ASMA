package game;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

import static config.Config.NUM_PLAYERS;
import static config.Config.NUM_ROUNDS;

public class Game {
    private int currentRound;
    private final int maxRounds;
    private boolean isOver;

    private final ArrayList<Player> players;
    private int currentPlayer;
    private final int numPlayers;
    private final ArrayList<Piece> islandPieces;

    private final AgentController agent;

    public Game(AgentContainer container) {
        this.maxRounds = NUM_ROUNDS;
        this.numPlayers = NUM_PLAYERS;
        this.currentRound = 1;
        this.currentPlayer = 1;

        this.islandPieces = new ArrayList<>();
        this.isOver = false;
        this.players = new ArrayList<>();

        for (int i = 0; i < this.numPlayers; i++) {
            try {
                this.players.add(new Player(container, i + 1));
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }

        Object[] args = new Object[1];
        args[0] = this;
        try {
            this.agent = container.createNewAgent("GameMaster", "agents.GameMaster", args);
            this.agent.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    private void nextRound() {
        if (this.currentRound == this.maxRounds) {
            this.currentRound = 1;
        } else {
            this.currentRound++;
        }
    }

    public void nextTurn() {
        if (this.currentPlayer == this.numPlayers) {
            this.currentPlayer = 1;
            nextRound();
        } else {
            this.currentPlayer++;
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public ArrayList<Piece> getIslandPieces() {
        return islandPieces;
    }
}
