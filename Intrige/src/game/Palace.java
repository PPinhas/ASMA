package game;

import java.util.ArrayList;

import static config.Config.PALACE_CARD_VALUES;

public class Palace {

    public static class Card {
        private final int value;
        private final Piece piece;

        public Card(int value) {
            this(value, null);
        }

        public Card(int value, Piece piece) {
            this.value = value;
            this.piece = piece;
        }

        public int getValue() {
            return value;
        }

        public Piece getPiece() {
            return piece;
        }
    }

    private final ArrayList<Piece> parkPieces;
    private final ArrayList<Card> cards;

    public Palace() {
        this.parkPieces = new ArrayList<>();
        this.cards = new ArrayList<>();

        for (int palaceCardValue : PALACE_CARD_VALUES) {
            cards.add(new Card(palaceCardValue));
        }
    }

    public ArrayList<Piece> getParkPieces() {
        return parkPieces;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
