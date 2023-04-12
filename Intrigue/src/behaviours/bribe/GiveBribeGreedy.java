package behaviours.bribe;

import agents.IntrigueAgent;
import config.GameConfig;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;

public class GiveBribeGreedy extends GiveBribe {

    public GiveBribeGreedy(IntrigueAgent intrigueAgent, ResolveConflict conflict) {
        super(intrigueAgent, conflict);
    }

    @Override
    protected BribeOffered offerBribe(int playerId) {
        int money = intrigueAgent.getOwnPlayer().getMoney();

        int largestBribe = GameConfig.MINIMUM_BRIBE - 1;
        for (Integer bribe : conflict.bribes().values()) {
            if (bribe > largestBribe) {
                largestBribe = bribe;
            }
        }
        largestBribe = Math.max(GameConfig.MINIMUM_BRIBE, largestBribe);

        int bribe = Math.min(money, largestBribe + 1);

        if (bribe < GameConfig.MINIMUM_BRIBE) bribe = 0;
        return new BribeOffered(playerId, bribe);
    }
}
