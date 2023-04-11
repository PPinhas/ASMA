package behaviours.bribe;

import agents.TrustAgent;
import config.GameConfig;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;

public class GiveBribeTrust extends GiveBribe {
    private final TrustAgent trustAgent;

    public GiveBribeTrust(TrustAgent trustAgent, ResolveConflict conflict) {
        super(trustAgent, conflict);
        this.trustAgent = trustAgent;
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

        int trustFactor = trustAgent.getTrustFactor(game.getPlayerById(playerId));
        int bribe = largestBribe + trustFactor * trustAgent.getConfig().bribeMultiplier();

        bribe = Math.min(money, bribe);
        if (bribe < GameConfig.MINIMUM_BRIBE) bribe = 0;
        return new BribeOffered(playerId, bribe);
    }
}
