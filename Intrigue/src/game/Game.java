package game;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;

import static config.GameConfig.NUM_PLAYERS;
import static config.GameConfig.NUM_ROUNDS;

public class Game {
    private int currentRound;
    private final int maxRounds;
    private boolean isOver;

    private final ArrayList<Player> players;
    private int currentPlayerId;
    private final int numPlayers;
    private final ArrayList<Piece> islandPieces;

    public Game(AgentContainer container) {
        this.maxRounds = NUM_ROUNDS;
        this.numPlayers = NUM_PLAYERS;
        this.currentRound = 1;
        this.currentPlayerId = (int) (Math.random() * this.numPlayers) + 1;

        this.islandPieces = new ArrayList<>();
        this.isOver = false;
        this.players = new ArrayList<>();

        for (int i = 0; i < this.numPlayers; i++) {
            try {
                this.players.add(new Player(container, i + 1, this));
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }

        Object[] args = new Object[1];
        args[0] = this;
        try {
            AgentController agent = container.createNewAgent("GameMaster", "agents.GameMaster", args);
            agent.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
    }

    private void nextRound() {
        if (this.currentRound == this.maxRounds) {
            endGame();
        } else {
            this.currentRound++;
        }
    }

    private void endGame() {
        Player winner = players.get(0);
        for (Player player : this.players) {
            if (player.getMoney() > winner.getMoney()) {
                winner = player;
            }
        }
        this.isOver = true;

        System.out.println("Game is over. The winner is player" + winner.getId() + " with " + winner.getMoney() + " money.");
    }

    public void nextTurn() {
        this.banishWaitingPieces();
        if (this.currentPlayerId == this.numPlayers) {
            this.currentPlayerId = 1;
            nextRound();
        } else {
            this.currentPlayerId++;
        }
    }

    public void collectIncome() {
        Palace palace = this.players.get(this.currentPlayerId - 1).getPalace();
        for (Palace.Card card : palace.getCards()) {
            Piece piece = card.getPiece();
            if (piece != null) {
                piece.getPlayer().moneyTransaction(card.getValue());
            }
        }
    }

    public void assignJob(int pieceIdx, int cardIndex) {
        Palace palace = this.getCurrentPlayer().getPalace();
        Piece replacedPiece = palace.assignPiece(pieceIdx, cardIndex);
        if (replacedPiece != null) {
            this.getIslandPieces().add(replacedPiece);
        }
    }

    public void seekJob(int playerIdx, int pieceIndex) {
        Piece piece = this.getCurrentPlayer().getPieces().remove(pieceIndex);
        this.players.get(playerIdx).getPalace().addWaitingPiece(piece);
    }

    public void transferBribe(int playerIdx, int amount) {
        this.getCurrentPlayer().moneyTransaction(amount);
        this.getPlayers().get(playerIdx).moneyTransaction(-amount);
    }

    public void banishWaitingPieces() {
        Palace palace = this.getCurrentPlayer().getPalace();
        for (int i = 0; i < palace.getParkPieces().size(); i++) {
            Piece piece = palace.getParkPieces().remove(i);
            this.islandPieces.add(piece);
        }
    }

    public boolean isOver() {
        return isOver;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public Player getCurrentPlayer() {
        return this.players.get(this.currentPlayerId - 1);
    }

    public ArrayList<Piece> getIslandPieces() {
        return islandPieces;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Player getPlayerById(int id) {
        for (Player player : this.players) {
            if (player.getId() == id) {
                return player;
            }
        }
        return null;
    }
}
