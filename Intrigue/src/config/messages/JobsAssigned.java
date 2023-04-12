package config.messages;

import java.io.Serializable;
import java.util.List;

public record JobsAssigned(
        List<Integer> selectedPieceIndices,
        List<Integer> cardIndices,
        List<Integer> selectedPieceOwners // IDs
) implements Serializable {
}
