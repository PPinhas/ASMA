package agents;

import behaviours.assign.AssignJobsPhase;
import behaviours.assign.trust.AssignJobsPhaseTrust;
import behaviours.bribe.GiveBribe;
import behaviours.bribe.GiveBribeTrust;
import behaviours.seek.SeekJobs;
import behaviours.seek.SeekJobsTrust;
import config.TrustAgentConfig;
import config.messages.ResolveConflict;
import game.Player;

import java.util.HashMap;
import java.util.Map;

public class TrustAgent extends IntrigueAgent {
    private final Map<Player, Integer> trustFactors = new HashMap<>();
    private TrustAgentConfig config;

    @Override
    protected void setup() {
        super.setup();
        for (Player player : game.getPlayers()) {
            trustFactors.put(player, 0);
        }
        if (getArguments().length >= 3)
            this.config = (TrustAgentConfig) getArguments()[2];
        else
            this.config = new TrustAgentConfig(); // Default
    }

    @Override
    protected AssignJobsPhase getAssignJobsBehaviour() {
        return new AssignJobsPhaseTrust(this);
    }

    @Override
    protected SeekJobs getSeekJobsBehaviour() {
        return new SeekJobsTrust(this);
    }

    @Override
    protected GiveBribe getResolveConflictBehaviour(ResolveConflict conflict) {
        return new GiveBribeTrust(this, conflict);
    }

    public Integer getTrustFactor(Player player) {
        return trustFactors.get(player);
    }

    public TrustAgentConfig getConfig() {
        return config;
    }
}
