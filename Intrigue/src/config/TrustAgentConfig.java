package config;

public record TrustAgentConfig(
        double bribeGivenMultiplier,
        double assignedJobMultiplier,
        double bribeReceivedMultiplier,
        int bribeAcceptedScore,
        int bribeRejectedScore
) {
    public TrustAgentConfig() {
        this(2, 0.1, 0.1, 100, -50);
    }
}
