package intrige.game;
import java.util.ArrayList;
import java.util.HashMap;

public class Palace {

    private enum Player{
        One, Two, Three, Four, Five
    };

    private Player player;

    private ArrayList<Piece> park;
    private HashMap<Integer, Piece> cards;

    public Player getPlayer() {return player;}

    public Palace(int player){
        this.player = Player.values()[player];
        this.park = new ArrayList<Piece>();
        this.cards = new HashMap<Integer, Piece>();
        cards.put(1000, null);
        cards.put(3000, null);
        cards.put(6000, null);
        cards.put(10000, null);
    }
}
