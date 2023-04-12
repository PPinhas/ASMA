package behaviours.seek;

import agents.TrustAgent;
import config.messages.EmployeesSent;
import game.Palace;
import game.Piece;
import game.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class SeekJobsTrust extends SeekJobs {
    private final TrustAgent trustAgent;

    public SeekJobsTrust(TrustAgent trustAgent) {
        super(trustAgent);
        this.trustAgent = trustAgent;
    }

    @Override
    public EmployeesSent seekJobs() {
        ArrayList<Piece> availablePieces = new ArrayList<>(intrigueAgent.getOwnPlayer().getPieces());
        if (availablePieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        Player p1 = trustAgent.getMostTrustedPlayer();
        int trust1 = trustAgent.getTrustFactor(p1);
        trustAgent.getTrustFactors().remove(p1);
        Player p2 = trustAgent.getMostTrustedPlayer();
        trustAgent.getTrustFactors().put(p1, trust1);

        for (Player player : Arrays.asList(p1, p2)) {
            selectPieceAndPalace(player, availablePieces);
        }

        return new EmployeesSent(pieceIndices, playerIndices);
    }

    public void selectPieceAndPalace(Player player, ArrayList<Piece> availablePieces) {
        Piece chosenPiece = null;
        int bestPayment = -1;
        boolean foundFreeJob = false;

        for (Piece piece : availablePieces) {
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
                }
            }
        }

        pieceIndices.add(intrigueAgent.getOwnPlayer().getPieces().indexOf(chosenPiece));
        playerIndices.add(intrigueAgent.getGame().getPlayers().indexOf(player));
        availablePieces.remove(chosenPiece);
    }
}
