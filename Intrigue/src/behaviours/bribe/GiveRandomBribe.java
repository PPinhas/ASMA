package behaviours.bribe;

import agents.IntrigueAgent;
import config.messages.BribeOffered;

public class GiveRandomBribe extends GiveBribe {

    public GiveRandomBribe(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    protected BribeOffered offerBribe(int playerIdx) {
        int money = intrigueAgent.getOwnPlayer().getMoney();
        int bribe = (int) (Math.random() * money);
        return new BribeOffered(playerIdx, bribe);
    }
}
