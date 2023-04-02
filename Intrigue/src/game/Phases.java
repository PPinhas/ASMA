package game;

public class Phases {

    private enum phase {
        CollectIncome,
        AssignJobs,
        ConflictResolution,
        Negotiation
    }

    private phase currentPhase;

    public Phases() {
        currentPhase = phase.CollectIncome;
    }

    private void nextPhase() {
        switch (currentPhase) {
            case CollectIncome:
                currentPhase = phase.AssignJobs;
                break;
            case AssignJobs:
                currentPhase = phase.ConflictResolution;
                break;
            case ConflictResolution:
                currentPhase = phase.Negotiation;
                break;
            case Negotiation:
                currentPhase = phase.CollectIncome;
                break;
        }
    }

    public void playPhase(Player player) {
        switch (currentPhase) {
            case CollectIncome:
                collectIncome(player);
                break;
            case AssignJobs:
                assignJobs();
                break;
            case ConflictResolution:
                conflictResolution();
                break;
            case Negotiation:
                negotiation();
                break;
        }
        nextPhase();
    }

    protected void collectIncome(Player player) {
        player.moneyTransaction(10000);
    }

    protected void assignJobs() {
    }

    protected void conflictResolution() {// might be a boolean function
    }

    protected void negotiation() {
    }
}