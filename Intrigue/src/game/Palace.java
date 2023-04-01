package game;

import game.conflict.ExternalConflict;
import game.conflict.InternalConflict;

import java.util.ArrayList;
import java.util.TreeSet;

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

    public TreeSet<InternalConflict> getInternalConflicts() {
        TreeSet<InternalConflict> internalConflicts = new TreeSet<>();
        for (Card card : cards) {
            Piece piece = card.getPiece();
            if (piece == null) continue;

            InternalConflict conflict = new InternalConflict(piece.getJob(), card.getValue(), piece.getPlayer());
            for (Piece parkPiece : parkPieces) {
                if (parkPiece.getJob() == piece.getJob()) {
                    conflict.addPlayer(parkPiece.getPlayer());
                }
            }

            if (conflict.getPlayers().size() == 0) continue;
            internalConflicts.add(conflict);
        }

        return internalConflicts;
    }

    public ArrayList<ExternalConflict> getExternalConflicts() {
        ArrayList<Piece.Job> takenJobs = new ArrayList<>();
        for (Card card : cards) {
            Piece piece = card.getPiece();
            if (piece != null) takenJobs.add(piece.getJob());
        }

        ArrayList<ExternalConflict> externalConflicts = new ArrayList<>();
        for (Piece piece : parkPieces) {
            if (takenJobs.contains(piece.getJob())) continue;

            boolean newConflict = true;
            for (ExternalConflict conflict : externalConflicts) {
                if (conflict.getJob() == piece.getJob()) {
                    conflict.addPlayer(piece.getPlayer());
                    newConflict = false;
                    break;
                }
            }

            if (newConflict) {
                ExternalConflict conflict = new ExternalConflict(piece.getJob());
                conflict.addPlayer(piece.getPlayer());
                externalConflicts.add(conflict);
            }
        }

        externalConflicts.removeIf(conflict -> conflict.getPlayers().size() <= 1);
        return externalConflicts;
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
