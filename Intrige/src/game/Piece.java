package game;

public class Piece {
    public enum Job {
        Scribe,
        Minister,
        Alchemist,
        Healer
    }

    private final Job job;
    private final Player player;

    public Piece(Job job, Player player) {
        this.job = job;
        this.player = player;
    }

    public Job getJob() {
        return job;
    }
}
