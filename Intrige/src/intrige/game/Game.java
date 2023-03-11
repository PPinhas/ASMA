package intrige.game;

import java.util.ArrayList;

public class Game {
    private enum Round {
        One, Two, Three, Four, Five
    };
    private Round round;

    private ArrayList<Piece> island;

    private enum Turn{
        One, Two, Three, Four, Five
    };
    private Turn turn;
    private ArrayList<Palace> palaces;
    private boolean isOver;

    private ArrayList<Player> players;

    public Game(){
        this.island = new ArrayList<Piece>();
        this.isOver = false;
        palaces = new ArrayList<Palace>();
        players = new ArrayList<Player>();
        for(int i = 0; i < 5; i++){
            palaces.add(new Palace(i + 1));
            players.add(new Player());
        }
        this.round = Round.One;
        this.turn = Turn.One;
    }

    public boolean isOver() {return isOver;}

    public void playRound(){
        do{

            do {
                playTurn();
                nextTurn();
            } while (turn != Turn.One);

            nextRound();
        } while(round != Round.One);
        isOver = true;;
    }

    private void nextRound() {
        if(this.round == Round.Five){
            this.round = Round.One;
        }
        else{
            this.round = Round.values()[round.ordinal() + 1];
        }
    }

    private void playTurn() {
        System.out.println("Round: " + round + " Turn: " + turn);

        this.players.get(turn.ordinal()).playTurn();

    }

    private void nextTurn(){
        if(this.turn == Turn.Five){
            this.turn = Turn.One;
        }
        else{
            this.turn = Turn.values()[turn.ordinal() + 1];
        }
    }
}
