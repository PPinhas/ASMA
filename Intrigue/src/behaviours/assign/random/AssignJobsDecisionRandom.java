package behaviours.assign.random;

import agents.IntrigueAgent;
import behaviours.assign.AssignJobsDecision;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;

import java.util.ArrayList;

public class AssignJobsDecisionRandom extends AssignJobsDecision {
    public AssignJobsDecisionRandom(IntrigueAgent intrigueAgent, Game game) {
        super(intrigueAgent, game);
    }

    @Override
    protected JobsAssigned assignJobs(Palace palace) {
        ArrayList<Palace.Card> availableCards = new ArrayList<>(palace.getEmptyCards());

        for (int i = 0; i < palace.getParkPieces().size(); i++) {
            pieceIndices.add(i);
            int availableCardIdx = (int) (Math.random() * availableCards.size());
            int realCardIdx = palace.getCards().indexOf(availableCards.get(availableCardIdx));
            cardIndices.add(realCardIdx);
            availableCards.remove(availableCardIdx);
            pieceOwners.add(palace.getParkPieces().get(i).getPlayer().getId());
        }

        return new JobsAssigned(pieceIndices, cardIndices, pieceOwners);
    }
}
