package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static config.GameConfig.PALACE_CARD_VALUES;

public class Palace {

    public static class Card {
        private final int value;
        private Piece piece;

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

        public void setPiece(Piece piece) {
            this.piece = piece;
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

    public HashMap<Piece.Job, ArrayList<Player>> getInternalConflicts() {
        HashMap<Piece.Job, ArrayList<Player>> playersPerJob = new HashMap<>();
        for (Card card : cards) {
            Piece piece = card.getPiece();
            if (piece == null) continue;

            ArrayList<Player> conflicts = new ArrayList<>();
            for (Piece parkPiece : parkPieces) {
                if (parkPiece.getJob() == piece.getJob()) {
                    conflicts.add(parkPiece.getPlayer());
                }
            }
            if (conflicts.size() == 0) continue;

            Collections.sort(conflicts);
            conflicts.add(0, piece.getPlayer());
            playersPerJob.put(piece.getJob(), conflicts);
        }

        return playersPerJob;
    }

    public HashMap<Piece.Job, ArrayList<Player>> getExternalConflicts() {
        HashMap<Piece.Job, ArrayList<Player>> playersPerJob = new HashMap<>();
        for (Piece piece : parkPieces) {
            if (!playersPerJob.containsKey(piece.getJob())) {
                playersPerJob.put(piece.getJob(), new ArrayList<>());
            }
            playersPerJob.get(piece.getJob()).add(piece.getPlayer());
        }

        playersPerJob.values().stream()
                .filter(players -> players.size() == 1)
                .forEach(Collections::sort);

        return playersPerJob;
    }

    public ArrayList<Piece> getParkPieces() {
        return parkPieces;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addWaitingPiece(Piece piece) {
        this.parkPieces.add(piece);
    }

    /**
     * Assign a piece to a card and returns the piece that was replaced
     */
    public Piece assignPiece(Piece piece, Card card) {
        Piece replacedPiece = card.getPiece();
        card.setPiece(piece);
        this.parkPieces.remove(piece);
        return replacedPiece;
    }

    /**
     * Assign a piece to a card and returns the piece that was replaced (by index)
     */
    public Piece assignPiece(int pieceIndex, int cardIndex) {
        Piece piece = this.parkPieces.get(pieceIndex);
        Card card = this.cards.get(cardIndex);
        return this.assignPiece(piece, card);
    }
}
