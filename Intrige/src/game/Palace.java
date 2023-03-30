package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Palace {
    private final ArrayList<Piece> parkPieces;
    private final HashMap<Integer, Piece> cards;

    public Palace() {
        this.parkPieces = new ArrayList<>();
        this.cards = new HashMap<>();

        cards.put(1000, null);
        cards.put(3000, null);
        cards.put(6000, null);
        cards.put(10000, null);
    }

    public ArrayList<Piece> getParkPieces() {
        return parkPieces;
    }

    public HashMap<Integer, Piece> getCards() {
        return cards;
    }
}
