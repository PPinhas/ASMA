package config;

public record TrustAgentConfig(
        int bribeMultiplier
) {
    public TrustAgentConfig() {
        this(1);
    }
}
