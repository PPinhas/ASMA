package game;

import config.GameExporter;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.ArrayList;
import java.util.List;

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


    public Game(AgentContainer container, boolean createAgents, String[] agentTypes) {
        this.maxRounds = NUM_ROUNDS;
        this.numPlayers = NUM_PLAYERS;
        this.currentRound = 1;
        this.currentPlayerId = 1;

        this.islandPieces = new ArrayList<>();
        this.isOver = false;
        this.players = new ArrayList<>();

        for (int i = 0; i < this.numPlayers; i++) {
            try {
                this.players.add(new Player(container, i + 1, this, createAgents, agentTypes[i]));
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
    }

    public Game(AgentContainer containerController, boolean createAgents) {
        this(containerController, createAgents, new String[]{"","","","",""});
    }

    private void nextRound() {
        if (this.currentRound == this.maxRounds) {
            this.isOver = true;
        } else {
            this.currentRound++;
        }
    }

    public void endGame() {
        String[] data = new String[5];
        collectIncome();
        Player winner = players.get(0);

        for(int i = 0; i < this.players.size(); i++){
            data[i] = String.valueOf(this.players.get(i).getMoney());
            if(this.players.get(i).getMoney() > winner.getMoney()){
                winner = this.players.get(i);
            }
        }

        this.display();
        System.out.println("Game is over. The winner is player" + winner.getId() + " with " + winner.getMoney() + " money.");
        new GameExporter(data);
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

    public void assignJob(Piece piece, Palace.Card card) {
        Palace palace = this.getCurrentPlayer().getPalace();
        if (piece != null) { // null means the job owner stays the same
            Piece replacedPiece = palace.assignPiece(piece, card);
            if (replacedPiece != null) {
                this.getIslandPieces().add(replacedPiece);
            }
        }

        // banish conflict pieces
        List<Piece> parkPieces = new ArrayList<>(palace.getParkPieces());
        for (Piece p : parkPieces) {
            if (p.getJob().equals(card.getPiece().getJob())) {
                palace.getParkPieces().remove(p);
                this.islandPieces.add(p);
            }
        }
    }

    public void seekJob(int playerIdx, Piece piece) {
        this.getCurrentPlayer().getPieces().remove(piece);
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


    public void display() {
        /*
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

        System.out.println(board);
        */
    }
}
