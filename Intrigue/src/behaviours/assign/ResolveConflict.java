package behaviours.assign;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.GameConfig;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import game.Piece;
import game.Player;
import game.conflict.Conflict;
import game.conflict.InternalConflict;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ResolveConflict extends SequentialBehaviour {
    protected final Game game;
    protected final Conflict conflict;
    protected final IntrigueAgent intrigueAgent;

    private final MessageTemplate template = BehaviourUtils.buildMessageTemplate(
            ACLMessage.INFORM,
            Protocols.OFFER_BRIBE_TO_PLAYER
    );

    public ResolveConflict(IntrigueAgent agent, Game game, Conflict conflict) {
        super(agent);
        this.intrigueAgent = agent;
        this.game = game;
        this.conflict = conflict;
    }

    public void onStart() {
        if (conflict instanceof InternalConflict internalConflict) {
            this.addSubBehaviour(new AskForBribe(intrigueAgent, internalConflict.getJobHolder()));
        }
        for (Player player : conflict.getPlayers()) {
            this.addSubBehaviour(new AskForBribe(intrigueAgent, player));
        }
        this.addSubBehaviour(new AssignJobs(intrigueAgent));
    }

    protected abstract JobsAssigned resolveConflict();

    protected class AskForBribe extends OneShotBehaviour {
        private final Player player;
        private final IntrigueAgent intrigueAgent;

        public AskForBribe(IntrigueAgent intrigueAgent, Player player) {
            super(intrigueAgent);
            this.intrigueAgent = intrigueAgent;
            this.player = player;
        }

        @Override
        public void action() {
            AID receiver = intrigueAgent.getAgentByPlayerId(player.getId());
            config.messages.ResolveConflict resolveConflictMsg = new config.messages.ResolveConflict(conflict);
            block(GameConfig.ACTION_DELAY_MS);
            ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.REQUEST, Protocols.RESOLVE_CONFLICT, resolveConflictMsg, Collections.singletonList(receiver));
            intrigueAgent.send(msg);

            ACLMessage reply;
            do {
                reply = intrigueAgent.receive(template);
            } while (reply == null || !reply.getSender().getLocalName().equals(receiver.getLocalName()));

            BribeOffered bribeOffered;
            try {
                bribeOffered = (BribeOffered) reply.getContentObject();
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }

            conflict.addBribe(player.getId(), bribeOffered.amount());
            block(GameConfig.ACTION_DELAY_MS);
        }
    }

    protected JobsAssigned buildJobsAssignedFromChosenPlayer(Player chosenPlayer) {
        Palace palace = intrigueAgent.getOwnPlayer().getPalace();
        ArrayList<Piece> parkPieces = palace.getParkPieces();

        int pieceIdx = -1;
        for (Piece piece : parkPieces) {
            if (piece.getPlayer().equals(chosenPlayer)) {
                pieceIdx = parkPieces.indexOf(piece);
            }
        }

        Integer cardIdx = getCardIdxFromJob(palace, conflict.getJob()); // internal conflict
        if (cardIdx == null) { // external conflict
            ArrayList<Palace.Card> emptyCards = palace.getEmptyCards();
            if (emptyCards.size() == 0) return null; // Impossible, or there would be an internal conflict
            cardIdx = (int) (Math.random() * emptyCards.size());
        }

        return new JobsAssigned(Collections.singletonList(pieceIdx), Collections.singletonList(cardIdx), Collections.singletonList(chosenPlayer.getId()));
    }

    protected Integer getCardIdxFromJob(Palace palace, Piece.Job job) {
        for (Palace.Card card : palace.getCards()) {
            if (card.getPiece() != null && card.getPiece().getJob().equals(job)) {
                return palace.getCards().indexOf(card);
            }
        }

        return null;
    }

    protected class AssignJobs extends OneShotBehaviour {

        public AssignJobs(IntrigueAgent intrigueAgent) {
            super(intrigueAgent);
        }

        @Override
        public void action() {
            JobsAssigned jobsAssigned = resolveConflict();
            if (jobsAssigned.selectedPieceIndices().isEmpty()) return;
            System.out.println("Jobs assigned (conflict): " + jobsAssigned.selectedPieceIndices());
            block(GameConfig.ACTION_DELAY_MS);

            List<AID> receivers = new ArrayList<>(intrigueAgent.getAgents());
            receivers.remove(intrigueAgent.getAID());
            ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.JOBS_ASSIGNED, jobsAssigned, receivers);
            intrigueAgent.send(msg);

            List<Piece> pieces = new ArrayList<>();
            List<Palace.Card> cards = new ArrayList<>();
            for (int i = 0; i < jobsAssigned.selectedPieceIndices().size(); i++) {
                pieces.add(game.getCurrentPlayer().getPalace().getParkPieces().get(jobsAssigned.selectedPieceIndices().get(i)));
                cards.add(game.getCurrentPlayer().getPalace().getCards().get(jobsAssigned.cardIndices().get(i)));
            }

            for (int i = 0; i < pieces.size(); i++) {
                game.assignJob(pieces.get(i), cards.get(i));
            }

        }
    }
}
