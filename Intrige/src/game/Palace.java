package game;

import java.util.ArrayList;
import java.util.HashMap;

import static config.Config.PALACE_CARD_VALUES;

public class Palace {
    private final ArrayList<Piece> parkPieces;
    private final HashMap<Integer, Piece> cards;

    public Palace() {
        this.parkPieces = new ArrayList<>();
        this.cards = new HashMap<>();

        for (int palaceCardValue : PALACE_CARD_VALUES) {
            cards.put(palaceCardValue, null);
        }
    }

    public ArrayList<Piece> getParkPieces() {
        return parkPieces;
    }

    public HashMap<Integer, Piece> getCards() {
        return cards;
    }
}
