package behaviours.assign.random;

import agents.IntrigueAgent;
import behaviours.assign.ResolveConflict;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import game.Piece;
import game.Player;
import game.conflict.Conflict;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Randomly chooses a player to assign a job to. Never chooses the current job holder in internal conflicts.
 */
public class ResolveConflictRandom extends ResolveConflict {

    public ResolveConflictRandom(IntrigueAgent agent, Game game, Conflict conflict) {
        super(agent, game, conflict);
    }

    @Override
    protected JobsAssigned resolveConflict() {
        Player[] conflictPlayers = conflict.getPlayers().toArray(new Player[0]);
        Player chosenPlayer = conflictPlayers[(int) (Math.random() * conflictPlayers.length)];

        Palace palace = intrigueAgent.getOwnPlayer().getPalace();
        ArrayList<Piece> parkPieces = palace.getParkPieces();

        int pieceIdx = -1;
        for (Piece piece : parkPieces) {
            if (piece.getPlayer().equals(chosenPlayer)) {
                pieceIdx = parkPieces.indexOf(piece);
            }
        }

        Integer cardIdx = getCardIdxFromJob(palace, conflict.getJob());
        if (cardIdx == null) {
            ArrayList<Palace.Card> emptyCards = palace.getEmptyCards();
            if (emptyCards.size() == 0) return null;
            cardIdx = (int) (Math.random() * emptyCards.size());
        }

        return new JobsAssigned(Collections.singletonList(pieceIdx), Collections.singletonList(cardIdx));
    }

    protected Integer getCardIdxFromJob(Palace palace, Piece.Job job) {
        for (Palace.Card card : palace.getCards()) {
            if (card.getPiece().getJob().equals(job)) {
                return palace.getCards().indexOf(card);
            }
        }

        return null;
    }
}
