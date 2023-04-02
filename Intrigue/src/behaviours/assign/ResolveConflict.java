package behaviours.assign;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.JobsAssigned;
import game.Game;
import game.Player;
import game.conflict.Conflict;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.Collections;

public abstract class ResolveConflict extends SequentialBehaviour {
    protected final Game game;
    protected final Conflict conflict;
    protected final IntrigueAgent intrigueAgent;

    public ResolveConflict(IntrigueAgent agent, Game game, Conflict conflict) {
        super(agent);
        this.intrigueAgent = agent;
        this.game = game;
        this.conflict = conflict;
    }

    public void onStart() {
        for (Player player : conflict.getPlayers()) {
            this.addSubBehaviour(new AskForBribe(intrigueAgent, player));
        }
        // TODO Ensure if there are no race conditions, since we need our own message to update the game.
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
            ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.REQUEST, Protocols.RESOLVE_CONFLICT, Collections.singletonList(receiver));
            intrigueAgent.send(msg);

            ACLMessage reply;
            do {
                reply = intrigueAgent.blockingReceive();
            } while (!reply.getSender().getLocalName().equals(receiver.getLocalName()) || !reply.getProtocol().equals(Protocols.BRIBE_OFFERED));

            // TODO Check if we can read the same message here and in GameUpdateListener. Otherwise, we need to send 2 messages in GiveBribe behaviour.
            BribeOffered bribeOffered;
            try {
                bribeOffered = (BribeOffered) reply.getContentObject();
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }

            conflict.getBribes().put(player, bribeOffered.amount());
        }
    }

    protected class AssignJobs extends OneShotBehaviour {
        public AssignJobs(IntrigueAgent intrigueAgent) {
            super(intrigueAgent);
        }

        @Override
        public void action() {
            JobsAssigned jobsAssigned = resolveConflict();
            if (jobsAssigned.selectedPieceIndices().isEmpty()) return;
            ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.JOBS_ASSIGNED, jobsAssigned, intrigueAgent.getAgents());
            intrigueAgent.send(msg);
        }
    }
}