package config.messages;

import java.io.Serializable;

public record BribeOffered(
        int playerId,
        int amount
) implements Serializable {
}
