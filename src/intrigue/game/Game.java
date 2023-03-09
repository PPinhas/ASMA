package intrigue.game;

import java.util.ArrayList;

public class Game {
    private enum round {
        One, Two, Three, Four, Five
    };

    private ArrayList<Piece> island;

    private enum turn{
        One, Two, Three, Four, Five
    };
    private ArrayList<Palace> palaces;
    private boolean isOver;

    private ArrayList<Player> players;

    public Game(){
        this.island = new ArrayList<Piece>();
        this.isOver = false;
        palaces = new ArrayList<Palace>();
        players = new ArrayList<Player>();
        for(int i = 0; i < 5; i++){
            palaces.add(new Palace());
            players.add(new Player());
        }

    }

    public boolean isOver() {return isOver;}

    public void playRound(){
        if(round = 5)
    }
}
