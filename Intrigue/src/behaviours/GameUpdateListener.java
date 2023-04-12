package behaviours;

import agents.InformedAgent;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.EmployeesSent;
import config.messages.JobsAssigned;
import game.Game;
import game.Palace;
import game.Piece;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.List;

public class GameUpdateListener extends CyclicBehaviour {
    private final Game game;

    private final MessageTemplate template = BehaviourUtils.buildMessageTemplate(
            ACLMessage.INFORM,
            Protocols.NEW_TURN,
            Protocols.COLLECT_INCOME,
            Protocols.JOBS_ASSIGNED,
            Protocols.EMPLOYEES_SENT,
            Protocols.BRIBE_OFFERED
    );

    public GameUpdateListener(InformedAgent informedAgent) {
        super(informedAgent);
        this.game = informedAgent.getGame();
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(template);
        if (msg == null) {
            block(); // TODO Consider using a delay
            return;
        }

        switch (msg.getProtocol()) {
            case Protocols.NEW_TURN -> this.handleNewTurn(msg);
            case Protocols.COLLECT_INCOME -> this.handleCollectIncome(msg);
            case Protocols.JOBS_ASSIGNED -> this.handleJobsAssigned(msg);
            case Protocols.EMPLOYEES_SENT -> this.handleEmployeesSent(msg);
            case Protocols.BRIBE_OFFERED -> this.handleBribeOffered(msg);
        }
    }

    /**
     * Handle the message sent when jobs are assigned by the current player.
     */
    protected void handleJobsAssigned(ACLMessage msg) {
        JobsAssigned info;
        try {
            info = (JobsAssigned) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        if (info.selectedPieceIndices().size() != info.cardIndices().size()) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        List<Piece> pieces = new ArrayList<>();
        List<Palace.Card> cards = new ArrayList<>();
        for (int i = 0; i < info.selectedPieceIndices().size(); i++) {
            if (info.selectedPieceIndices().get(i) == -1) { // job holder stays the same
                pieces.add(null);
            } else {
                pieces.add(this.game.getCurrentPlayer().getPalace().getParkPieces().get(info.selectedPieceIndices().get(i)));
            }
            cards.add(this.game.getCurrentPlayer().getPalace().getCards().get(info.cardIndices().get(i)));
        }

        for (int i = 0; i < pieces.size(); i++) {
            this.game.assignJob(pieces.get(i), cards.get(i));
        }
    }

    /**
     * Handle the message sent when employees are sent by the current player.
     */
    protected void handleEmployeesSent(ACLMessage msg) {
        EmployeesSent info;
        try {
            info = (EmployeesSent) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        if (info.pieceIndices().size() != info.playerIndices().size()) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < info.pieceIndices().size(); i++) {
            pieces.add(this.game.getCurrentPlayer().getPieces().get(info.pieceIndices().get(i)));
        }

        for (int i = 0; i < info.playerIndices().size(); i++) {
            this.game.seekJob(info.playerIndices().get(i), pieces.get(i));
        }
    }

    /**
     * Handle the message sent when a bribe is offered to the current player.
     */
    protected void handleBribeOffered(ACLMessage msg) {
        BribeOffered info;
        try {
            info = (BribeOffered) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        this.game.transferBribe(info.playerId(), info.amount());
    }

    protected void handleNewTurn(ACLMessage msg) {
        this.game.nextTurn();
    }

    protected void handleCollectIncome(ACLMessage msg) {
        this.game.collectIncome();
    }
}
