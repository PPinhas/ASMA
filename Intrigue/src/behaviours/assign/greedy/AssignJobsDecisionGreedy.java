package behaviours.assign.greedy;

import agents.IntrigueAgent;
import behaviours.assign.AssignJobsDecision;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import game.Piece;

import java.util.ArrayList;

public class AssignJobsDecisionGreedy extends AssignJobsDecision {
    public AssignJobsDecisionGreedy(IntrigueAgent intrigueAgent, Game game) {
        super(intrigueAgent, game);
    }

    @Override
    protected JobsAssigned assignJobs(Palace palace) {
        ArrayList<Palace.Card> availableCards = new ArrayList<>(palace.getEmptyCards());
        ArrayList<Piece> waitingPieces = new ArrayList<>(palace.getParkPieces());

        while (!availableCards.isEmpty()) {
            // get worst card and assign it to player with more money
            Palace.Card worstCard = availableCards.get(0);
            for (Palace.Card card : availableCards) {
                if (card.getValue() < worstCard.getValue()) {
                    worstCard = card;
                }
            }

            Piece richestPiece = waitingPieces.get(0);
            for (Piece piece : waitingPieces) {
                if (piece.getPlayer().getMoney() > richestPiece.getPlayer().getMoney()) {
                    richestPiece = piece;
                }
            }

            cardIndices.add(palace.getCards().indexOf(worstCard));
            pieceIndices.add(palace.getParkPieces().indexOf(richestPiece));
        }

        return new JobsAssigned(pieceIndices, cardIndices);
    }
}