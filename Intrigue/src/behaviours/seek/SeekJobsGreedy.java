package behaviours.seek;

import agents.IntrigueAgent;
import config.messages.EmployeesSent;
import game.Palace;
import game.Piece;
import game.Player;

import java.util.ArrayList;

public class SeekJobsGreedy extends SeekJobs {

    public SeekJobsGreedy(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    public EmployeesSent seekJobs() {
        ArrayList<Piece> availablePieces = new ArrayList<>(intrigueAgent.getOwnPlayer().getPieces());
        if (availablePieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        ArrayList<Player> availablePlayers = new ArrayList<>(intrigueAgent.getGame().getPlayers());
        availablePlayers.remove(intrigueAgent.getOwnPlayer());

        selectPieceAndPalace(availablePlayers, availablePieces);

        // try second employee
        if (availablePieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);
        selectPieceAndPalace(availablePlayers, availablePieces);

        return new EmployeesSent(pieceIndices, playerIndices);
    }

    /**
     * @return index of the selected piece
     */
    private void selectPieceAndPalace(ArrayList<Player> availablePlayers, ArrayList<Piece> availablePieces) {
        Piece chosenPiece = null;
        Player chosenPlayer = null;
        int bestPayment = -1;
        boolean foundFreeJob = false;

        for (Piece piece : availablePieces) {
            for (Player player : availablePlayers) {
                for (Palace.Card card : player.getPalace().getCards()) {
                    int payment = card.getValue();
                    boolean betterCard = false;
                    if (card.getPiece() == null) { // free
                        if (player.getPalace().hasJob(piece.getJob())) continue; // Can't employ the same job twice
                        if (!foundFreeJob || payment > bestPayment) {
                            betterCard = true;
                            foundFreeJob = true;
                        }
                    } else {
                        if (card.getPiece().getJob() != piece.getJob()) continue;
                        if (!foundFreeJob && payment > bestPayment) betterCard = true;
                    }

                    if (betterCard) {
                        bestPayment = payment;
                        chosenPiece = piece;
                        chosenPlayer = player;
                    }
                }
            }
        }

        pieceIndices.add(intrigueAgent.getOwnPlayer().getPieces().indexOf(chosenPiece));
        playerIndices.add(intrigueAgent.getGame().getPlayers().indexOf(chosenPlayer));
        availablePlayers.remove(chosenPlayer);
        availablePieces.remove(chosenPiece);
    }
}
