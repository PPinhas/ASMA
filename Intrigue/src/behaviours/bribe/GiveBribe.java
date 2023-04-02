package behaviours.bribe;

import agents.IntrigueAgent;
import behaviours.BehaviourUtils;
import config.Protocols;
import config.messages.BribeOffered;
import game.Game;
import game.Player;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public abstract class GiveBribe extends OneShotBehaviour {

    protected final Game game;
    protected final IntrigueAgent intrigueAgent;

    public GiveBribe(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
        this.intrigueAgent = intrigueAgent;
        this.game = intrigueAgent.getGame();
    }

    public void action() {
        BribeOffered bribeOffered = offerBribe(game.getCurrentPlayer());
        ACLMessage msg = BehaviourUtils.buildMessage(ACLMessage.INFORM, Protocols.BRIBE_OFFERED, bribeOffered, intrigueAgent.getAgents());
        intrigueAgent.send(msg);
    }

    protected abstract BribeOffered offerBribe(Player player);
}
