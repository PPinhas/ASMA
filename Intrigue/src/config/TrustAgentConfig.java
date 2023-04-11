package config;

public record TrustAgentConfig(
        double bribeMultiplier,
        double assignedJobMultiplier,
        int bribeAcceptedScore,
        int bribeRejectedScore
) {
    public TrustAgentConfig() {
        this(1, 0.05, 100, -100);
    }
}
