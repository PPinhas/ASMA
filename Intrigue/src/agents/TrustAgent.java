package agents;

import behaviours.GameUpdateListener;
import behaviours.assign.AssignJobsPhase;
import behaviours.assign.trust.AssignJobsPhaseTrust;
import behaviours.bribe.GiveBribe;
import behaviours.bribe.GiveBribeTrust;
import behaviours.seek.SeekJobs;
import behaviours.seek.SeekJobsTrust;
import config.GameConfig;
import config.TrustAgentConfig;
import config.messages.BribeOffered;
import config.messages.JobsAssigned;
import config.messages.ResolveConflict;
import game.Palace;
import game.Player;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrustAgent extends IntrigueAgent {
    private final Map<Player, Integer> trustFactors = new HashMap<>();
    private TrustAgentConfig config;

    @Override
    protected void setup() {
        super.setup();
        if (getArguments().length >= 3)
            this.config = (TrustAgentConfig) getArguments()[2];
        else
            this.config = new TrustAgentConfig(); // Default
        for (Player player : game.getPlayers()) {
            if (player.getId() == this.getId()) continue;
            trustFactors.put(player, 0);
        }
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

    public Map<Player, Integer> getTrustFactors() {
        return trustFactors;
    }

    public Player getMostTrustedPlayer() {
        Player mostTrustedPlayer = null;
        int mostTrust = trustFactors.values().stream().toList().get(0);
        for (Player player : trustFactors.keySet()) {
            if (trustFactors.get(player) >= mostTrust) {
                mostTrust = trustFactors.get(player);
                mostTrustedPlayer = player;
            }
        }
        return mostTrustedPlayer;
    }

    public void changeTrustFactor(Player player, int change) {
        int trustFactor = trustFactors.get(player);
        trustFactor += change;
        trustFactors.put(player, trustFactor);
    }

    @Override
    protected GameUpdateListener getGameUpdateListener() {
        return new TrustGameUpdateListener(this);
    }

    private class TrustGameUpdateListener extends GameUpdateListener {
        private final TrustAgent trustAgent;

        public TrustGameUpdateListener(TrustAgent agent) {
            super(agent);
            this.trustAgent = agent;
        }

        @Override
        protected void handleJobsAssigned(ACLMessage msg) {
            super.handleJobsAssigned(msg);
            JobsAssigned info;
            try {
                info = (JobsAssigned) msg.getContentObject();
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }

            int myIndex = -1;
            for (Integer i : info.selectedPieceOwners()) {
                if (i == trustAgent.getId()) {
                    myIndex = info.selectedPieceOwners().indexOf(i);
                    break;
                }
            }
            if (myIndex == -1) return;

            ArrayList<Palace.Card> cards = game.getCurrentPlayer().getPalace().getCards();
            int jobValue = cards.get(info.cardIndices().get(myIndex)).getValue();

            int totalValue = 0;
            for (Integer i : info.cardIndices()) {
                totalValue += cards.get(i).getValue();
            }
            int averageValue = totalValue / info.cardIndices().size();

            int factorDiff = (int) Math.round((jobValue - averageValue) * config.assignedJobMultiplier());
            trustAgent.changeTrustFactor(game.getCurrentPlayer(), factorDiff);

            System.out.println("trust factors player " + getId() + " " + trustAgent.getTrustFactors());
        }

        @Override
        protected void handleBribeOffered(ACLMessage msg) {
            super.handleBribeOffered(msg);
            BribeOffered info;
            try {
                info = (BribeOffered) msg.getContentObject();
            } catch (UnreadableException e) {
                throw new RuntimeException(e);
            }

            if (info.playerId() == trustAgent.getId()) return;
            int bribeDiff = info.amount() - GameConfig.MINIMUM_BRIBE;
            int factorDiff = (int) Math.round(bribeDiff * config.bribeReceivedMultiplier());
            trustAgent.changeTrustFactor(game.getCurrentPlayer(), factorDiff);
        }
    }
}
