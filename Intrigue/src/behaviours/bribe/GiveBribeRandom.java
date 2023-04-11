package behaviours.bribe;

import agents.IntrigueAgent;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;

public class GiveBribeRandom extends GiveBribe {

    public GiveBribeRandom(IntrigueAgent intrigueAgent, ResolveConflict conflict) {
        super(intrigueAgent, conflict);
    }

    @Override
    protected BribeOffered offerBribe(int playerIdx) {
        int money = intrigueAgent.getOwnPlayer().getMoney();
        int bribe = (int) (Math.random() * money);
        return new BribeOffered(playerIdx, bribe);
    }
}
