package intrige.game;
import java.util.ArrayList;
import java.util.HashMap;

public class Palace {

    private enum player{
        One, Two, Three, Four, Five
    };

    private ArrayList<Piece> park;
    private HashMap<Integer, Piece> cards;

    public Palace(){
        this.park = new ArrayList<Piece>();
        this.cards = new HashMap<Integer, Piece>();
        cards.put(1000, null);
        cards.put(3000, null);
        cards.put(6000, null);
        cards.put(10000, null);
    }
}
