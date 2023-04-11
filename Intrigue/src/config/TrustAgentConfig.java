package config;

public record TrustAgentConfig(
        double bribeGivenMultiplier,
        double assignedJobMultiplier,
        double bribeReceivedMultiplier,
        int bribeAcceptedScore,
        int bribeRejectedScore
) {
    public TrustAgentConfig() {
        this(1, 0.05, 0.05, 100, -100);
    }
}
