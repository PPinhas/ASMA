package behaviours.bribe;

import agents.TrustAgent;
import config.GameConfig;
import config.Protocols;
import config.messages.BribeOffered;
import config.messages.JobsAssigned;
import config.messages.ResolveConflict;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class GiveBribeTrust extends GiveBribe {
    private final TrustAgent trustAgent;
    private int playerId;

    public GiveBribeTrust(TrustAgent trustAgent, ResolveConflict conflict) {
        super(trustAgent, conflict);
        this.trustAgent = trustAgent;
    }

    @Override
    public void action() {
        super.action();

        ACLMessage reply;
        do {
            reply = intrigueAgent.blockingReceive();
        } while (!reply.getProtocol().equals(Protocols.JOBS_ASSIGNED));

        JobsAssigned jobsAssigned;
        try {
            jobsAssigned = (JobsAssigned) reply.getContentObject();
        } catch (UnreadableException e) {
            throw new RuntimeException(e);
        }

        boolean selected = false;
        for (Integer playerId : jobsAssigned.selectedPieceOwners()) {
            if (playerId == trustAgent.getId()) {
                selected = true;
                break;
            }
        }

        if (selected) { // TODO: Consider changing the factor based on other bribes' values
            trustAgent.changeTrustFactor(this.game.getPlayerById(playerId), trustAgent.getConfig().bribeAcceptedScore());
        } else {
            trustAgent.changeTrustFactor(this.game.getPlayerById(playerId), trustAgent.getConfig().bribeRejectedScore());
        }
    }

    @Override
    protected BribeOffered offerBribe(int playerId) {
        this.playerId = playerId;

        int money = intrigueAgent.getOwnPlayer().getMoney();
        int largestBribe = GameConfig.MINIMUM_BRIBE - 1;
        for (Integer bribe : conflict.bribes().values()) {
            if (bribe > largestBribe) {
                largestBribe = bribe;
            }
        }

        int trustFactor = trustAgent.getTrustFactor(game.getPlayerById(playerId));
        int bribe = largestBribe + (int) Math.round(trustFactor * trustAgent.getConfig().bribeMultiplier());

        bribe = Math.min(money, bribe);
        if (bribe < GameConfig.MINIMUM_BRIBE) bribe = 0;
        return new BribeOffered(playerId, bribe);
    }
}
