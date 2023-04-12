package behaviours.bribe;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.GameConfig;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;
import game.Game;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

public abstract class GiveBribe extends OneShotBehaviour {

    protected final Game game;
    protected final IntrigueAgent intrigueAgent;
    protected final ResolveConflict conflict;

    public GiveBribe(IntrigueAgent intrigueAgent, ResolveConflict conflict) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
        this.game = intrigueAgent.getGame();
        this.conflict = conflict;
    }

    public void action() {
        BribeOffered bribeOffered = offerBribe(game.getCurrentPlayerId());
        block(GameConfig.ACTION_DELAY_MS);
        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.BRIBE_OFFERED, bribeOffered, intrigueAgent.getAgents());
        intrigueAgent.send(msg);

        AID player = intrigueAgent.getAgentByPlayerId(bribeOffered.playerId());
        ACLMessage playerMsg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.OFFER_BRIBE_TO_PLAYER, bribeOffered, List.of(player));
        intrigueAgent.send(playerMsg);
    }

    protected abstract BribeOffered offerBribe(int playerId);
}
