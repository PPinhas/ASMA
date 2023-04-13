package game.conflict;

import game.Piece;
import game.Player;

public class InternalConflict extends Conflict implements Comparable<InternalConflict> {
    private final int value;
    private final Player jobHolder;

    public InternalConflict(Piece.Job job, int value, Player jobHolder) {
        super(job);
        this.value = value;
        this.jobHolder = jobHolder;
    }

    public int getValue() {
        return value;
    }

    public Player getJobHolder() {
        return jobHolder;
    }

    @Override
    public int compareTo(InternalConflict other) {
        return Integer.compare(value, other.value);
    }

    @Override
    public void addBribe(int playerId, int bribe) {
        try {
            super.addBribe(playerId, bribe);
        } catch (IllegalArgumentException e) {
            if (playerId == jobHolder.getId()) {
                bribes.put(jobHolder, bribe);
            } else {
                throw e;
            }
        }
    }
}
