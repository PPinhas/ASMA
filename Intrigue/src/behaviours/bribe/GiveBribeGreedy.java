package behaviours.bribe;

import agents.IntrigueAgent;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;

public class GiveBribeGreedy extends GiveBribe {

    public GiveBribeGreedy(IntrigueAgent intrigueAgent, ResolveConflict conflict) {
        super(intrigueAgent, conflict);
    }

    @Override
    protected BribeOffered offerBribe(int playerIdx) {
        int money = intrigueAgent.getOwnPlayer().getMoney();
        int largestBribe = 0;
        for (Integer bribe : conflict.bribes().values()) {
            if (bribe > largestBribe) {
                largestBribe = bribe;
            }
        }

        int bribe = Math.min(money, largestBribe + 1);
        return new BribeOffered(playerIdx, bribe);
    }
}
