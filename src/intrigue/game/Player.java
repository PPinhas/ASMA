package intrigue.game;

import java.util.ArrayList;
import static intrigue.game.Piece.Job.*;


public class Player {
    private ArrayList<Bill> money;
    private ArrayList<Piece> pieces;

    public Player(){
        this.money = new ArrayList<Bill>();
        this.pieces = new ArrayList<Piece>();
        for(int i = 0; i < 2; i++){
            this.money.add(new Bill(1000));
            this.money.add(new Bill(5000));
            this.money.add(new Bill(10000));

            this.pieces.add(new Piece(Scribe));
            this.pieces.add(new Piece(Minister));
            this.pieces.add(new Piece(Alchemist));
            this.pieces.add(new Piece(Healer));

        }
    }
}
