package config.messages;

import java.io.Serializable;
import java.util.List;

public record EmployeesSent(
        List<Integer> pieceIndices,
        List<Integer> playerIndices
) implements Serializable {
}
