package intrige.game;

public class Piece {
    public enum Job{
        Scribe,
        Minister,
        Alchemist,
        Healer
    }
    private Job job;
    public Piece(Job job){
        this.job = job;
    }

    public Job getJob(){
        return job;
    }
}
