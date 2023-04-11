package behaviours.bribe;

import agents.TrustAgent;
import config.messages.BribeOffered;
import config.messages.ResolveConflict;

public class GiveBribeTrust extends GiveBribe {

    public GiveBribeTrust(TrustAgent intrigueAgent, ResolveConflict conflict) {
        super(intrigueAgent, conflict);
    }

    @Override
    protected BribeOffered offerBribe(int playerIdx) {
        // TODO calculate the bribe based on the trust factor (some kind of linear function?)
        return new BribeOffered(playerIdx, 0);
    }
}
