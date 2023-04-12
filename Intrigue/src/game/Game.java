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
    private boolean started;

    private final ArrayList<Player> players;
    private int currentPlayerId;
    private final int numPlayers;
    private final ArrayList<Piece> islandPieces;

    public Game(AgentContainer container, boolean createAgents) {
        this.maxRounds = NUM_ROUNDS;
        this.numPlayers = NUM_PLAYERS;
        this.currentRound = 1;
        // TODO this.currentPlayerIdx = (int) (Math.random() * this.numPlayers) + 1;
        this.currentPlayerId = 1;

        this.islandPieces = new ArrayList<>();
        this.isOver = false;
        this.players = new ArrayList<>();
        this.started = false;

        for (int i = 0; i < this.numPlayers; i++) {
            try {
                this.players.add(new Player(container, i + 1, this, createAgents));
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }

        if (createAgents) {
            Object[] args = new Object[1];
            args[0] = this;
            try {
                AgentController agent = container.createNewAgent("GameMaster", "agents.GameMaster", args);
                agent.start();
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }
        displayGame();
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
        displayGame();
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

        System.out.println("player index " + playerIdx);
        System.out.println("piece index " + pieceIndex);
        System.out.println("current player ID " + this.getCurrentPlayer().getId());

        Piece piece;
        try {
            piece = this.getCurrentPlayer().getPieces().remove(pieceIndex);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Player " + this.getCurrentPlayer().getId() + " has no pieces to seek a job");
            return;
        }
        this.players.get(playerIdx).getPalace().addWaitingPiece(piece);
    }

    public void transferBribe(int playerId, int amount) {
        this.getCurrentPlayer().moneyTransaction(amount);
        this.getPlayers().get(playerId - 1).moneyTransaction(-amount);
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

    public boolean hasStarted() {
        return started;
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

    public void start() {
        this.started = true;
    }

    private void displayGame() {
        StringBuilder board = new StringBuilder();


        board.append("  +");
        for (int i = 0; i < 5; i++) {
            board.append("--------+");
        }
        board.append("\n");
        board.append(String.format("%-10s Round: %s", " ", this.currentRound));
        board.append(" | Turn: ");
        board.append(this.currentPlayerId);
        board.append("\n");

        if (!this.islandPieces.isEmpty()) {
            board.append("Pieces in the island:\n");
            for (Piece piece : this.islandPieces) {
                board.append(String.format("%s from Player %d \n", piece.getJob(), piece.getPlayer().getId()));
            }
        } else {
            board.append("No pieces in the island.\n");
        }


        for (int i = 0; i < this.numPlayers; i++) {
            String playerName = "Player " + (i + 1);
            Player player = this.players.get(i);
            board.append((playerName));
            board.append("\n");
            board.append(String.format("    | Money: %d", player.getMoney()));
            board.append("\n");
            board.append("    | Pieces: ");
            for (Piece piece : player.getPieces()) {
                board.append(String.format("%s|", piece.getJob()));
            }
            board.append('\n');
            Palace palace = player.getPalace();
            if (!palace.getParkPieces().isEmpty()) {
                board.append("    | Pieces in palace park:\n");
                for (Piece piece : palace.getParkPieces()) {
                    board.append(String.format("        %s from Player %d\n", piece.getJob(), piece.getPlayer().getId()));
                }
            } else {
                board.append("    | No pieces in palace park\n");
            }

            board.append("    | Card status:\n");
            for (Palace.Card card : palace.getCards()) {
                if (card.getPiece() == null) {
                    board.append(String.format("        Card of value %d is not occupied\n", card.getValue()));
                } else {
                    Piece piece = card.getPiece();
                    board.append(String.format("        Card of value %d is occupied by %s of %d\n", card.getValue(), piece.getJob(), piece.getPlayer().getId()));
                }

            }
        }

        board.append("  +");
        for (int i = 0; i < 5; i++) {
            board.append("--------+");
        }
        board.append("\n");

        System.out.println(board.toString());
    }
}
