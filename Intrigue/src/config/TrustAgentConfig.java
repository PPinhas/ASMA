package config;

public record TrustAgentConfig(
        int bribeMultiplier,
        int bribeAcceptedScore,
        int bribeRejectedScore
) {
    public TrustAgentConfig() {
        this(1, 100, -100);
    }
}
