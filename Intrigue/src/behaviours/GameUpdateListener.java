package behaviours;

import agents.IntrigueAgent;
import config.Protocols;
import game.Game;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

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

    public GameUpdateListener(IntrigueAgent intrigueAgent) {
        this.game = intrigueAgent.getGame();
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
        String[] info = msg.getContent().split("\n");
        if (info.length != 2) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        String[] pieceIndices = info[0].split(" ");
        String[] cardIndices = info[1].split(" ");
        if (pieceIndices.length != cardIndices.length) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        for (int i = 0; i < pieceIndices.length; i++) {
            int pieceIndex = Integer.parseInt(pieceIndices[i]);
            int cardIndex = Integer.parseInt(cardIndices[i]);
            this.game.assignJob(pieceIndex, cardIndex);
        }
    }

    /**
     * Handle the message sent when employees are sent by the current player.
     */
    private void handleEmployeesSent(ACLMessage msg) {
        String[] info = msg.getContent().split("\n");
        if (info.length != 2) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        String[] pieceIndices = info[0].split(" ");
        String[] playerIndices = info[1].split(" ");
        if (pieceIndices.length != playerIndices.length) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        for (int i = 0; i < pieceIndices.length; i++) {
            int pieceIndex = Integer.parseInt(pieceIndices[i]);
            int playerIndex = Integer.parseInt(playerIndices[i]);
            this.game.seekJob(pieceIndex, playerIndex);
        }
    }

    /**
     * Handle the message sent when a bribe is offered to the current player.
     */
    private void handleBribeOffered(ACLMessage msg) {
        String[] info = msg.getContent().split("\n");
        if (info.length != 2) {
            throw new RuntimeException("Invalid message content( " + msg.getProtocol() + "):\n" + msg.getContent());
        }

        int playerIndex = Integer.parseInt(info[0]);
        int amount = Integer.parseInt(info[1]);
        this.game.transferBribe(playerIndex, amount);
    }
}
