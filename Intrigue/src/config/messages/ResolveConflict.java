package config.messages;

import game.Piece;
import game.Player;
import game.conflict.Conflict;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record ResolveConflict(
        Piece.Job job,
        List<Integer> playerIndices,
        Map<Integer, Integer> bribes
) implements Serializable {
    public ResolveConflict(Conflict conflict) {
        this(conflict.getJob(), conflict.getPlayers().stream().map(Player::getId).toList(), new HashMap<>());
        for (Player player : conflict.getPlayers()) {
            Integer bribe = conflict.getBribe(player);
            if (bribe != null) {
                bribes.put(player.getId(), bribe);
            }
        }
    }
}
