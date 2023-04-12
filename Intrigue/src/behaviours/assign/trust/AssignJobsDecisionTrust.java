package behaviours.assign.trust;

import agents.TrustAgent;
import behaviours.assign.AssignJobsDecision;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import game.Piece;

import java.util.ArrayList;

public class AssignJobsDecisionTrust extends AssignJobsDecision {
    private final TrustAgent trustAgent;

    public AssignJobsDecisionTrust(TrustAgent trustAgent, Game game) {
        super(trustAgent, game);
        this.trustAgent = trustAgent;
    }

    @Override
    protected JobsAssigned assignJobs(Palace palace) {
        ArrayList<Palace.Card> availableCards = new ArrayList<>(palace.getEmptyCards());
        ArrayList<Piece> waitingPieces = new ArrayList<>(palace.getParkPieces());

        while (!waitingPieces.isEmpty()) {
            // get best card and assign it to player with more trust
            Palace.Card bestCard = availableCards.get(0);
            for (Palace.Card card : availableCards) {
                if (card.getValue() > bestCard.getValue()) {
                    bestCard = card;
                }
            }

            Piece privilegedPiece = waitingPieces.get(0);
            for (Piece piece : waitingPieces) {
                int playerTrust = trustAgent.getTrustFactor(piece.getPlayer());
                int currentTrust = trustAgent.getTrustFactor(privilegedPiece.getPlayer());
                if (playerTrust > currentTrust) {
                    privilegedPiece = piece;
                }
            }

            cardIndices.add(palace.getCards().indexOf(bestCard));
            pieceIndices.add(palace.getParkPieces().indexOf(privilegedPiece));
            pieceOwners.add(privilegedPiece.getPlayer().getId());
            availableCards.remove(bestCard);
            waitingPieces.remove(privilegedPiece);
        }

        return new JobsAssigned(pieceIndices, cardIndices, pieceOwners);
    }
}
