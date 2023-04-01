package config.messages;

import java.io.Serializable;

public record BribeOffered(
        int playerIdx,
        int amount
) implements Serializable {
}
