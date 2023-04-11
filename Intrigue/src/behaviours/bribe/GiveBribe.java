package behaviours.bribe;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;
import game.Game;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

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
        BribeOffered bribeOffered = offerBribe(game.getCurrentPlayerIdx());
        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.BRIBE_OFFERED, bribeOffered, intrigueAgent.getAgents());
        intrigueAgent.send(msg);
    }

    protected abstract BribeOffered offerBribe(int playerIdx);
}
