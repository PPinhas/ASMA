package behaviours;

import agents.InformedAgent;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.EmployeesSent;
import config.messages.JobsAssigned;
import game.Game;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

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
            case Protocols.NEW_TURN -> this.game.nextTurn();
            case Protocols.COLLECT_INCOME -> this.game.collectIncome();
            case Protocols.JOBS_ASSIGNED -> this.handleJobsAssigned(msg);
            case Protocols.EMPLOYEES_SENT -> this.handleEmployeesSent(msg);
            case Protocols.BRIBE_OFFERED -> this.handleBribeOffered(msg);
        }
    }

    /**
     * Handle the message sent when jobs are assigned by the current player.
     */
    private void handleJobsAssigned(ACLMessage msg) {
        JobsAssigned info;
        try {
            info = (JobsAssigned) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        if (info.selectedPieceIndices().size() != info.cardIndices().size()) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        for (int i = 0; i < info.selectedPieceIndices().size(); i++) {
            this.game.assignJob(info.selectedPieceIndices().get(i), info.cardIndices().get(i));
        }
        this.game.banishWaitingPieces();
    }

    /**
     * Handle the message sent when employees are sent by the current player.
     */
    private void handleEmployeesSent(ACLMessage msg) {
        EmployeesSent info;
        try {
            info = (EmployeesSent) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        if (info.pieceIndices().size() != info.playerIndices().size()) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        for (int i = 0; i < info.pieceIndices().size(); i++) {
            this.game.seekJob(info.playerIndices().get(i), info.pieceIndices().get(i));
        }
    }

    /**
     * Handle the message sent when a bribe is offered to the current player.
     */
    private void handleBribeOffered(ACLMessage msg) {
        BribeOffered info;
        try {
            info = (BribeOffered) msg.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        this.game.transferBribe(info.playerIdx(), info.amount());
    }
}
