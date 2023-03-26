package game;

import java.util.ArrayList;

import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;



public class Game {

    public enum Round {
        One, Two, Three, Four, Five
    };
    private Round round;

    private ArrayList<Piece> island;

    public enum Turn{
        One, Two, Three, Four, Five
    };
    private Turn turn;
    private ArrayList<Palace> palaces;
    private boolean isOver;

    private AgentController agent;

    private ArrayList<Player> players;

    public Game(AgentContainer container){
        this.island = new ArrayList<Piece>();
        this.isOver = false;
        palaces = new ArrayList<Palace>();
        players = new ArrayList<Player>();
        for(int i = 0; i < 5; i++){
            palaces.add(new Palace(i + 1));
            try {
                players.add(new Player(container, i + 1));
            } catch (StaleProxyException e) {
                throw new RuntimeException(e);
            }
        }
        this.round = Round.One;
        this.turn = Turn.One;
        Object[] args = new Object[1];
        args[0] = this;
        try {
            this.agent = container.createNewAgent("GameMaster", "agents.GameMaster", args);
            this.agent.start();
        } catch (StaleProxyException e) {
            throw new RuntimeException(e);
        }
        //this.setVisible(true);
    }

    public boolean isOver() {return isOver;}

    private void nextRound() {
        if(this.round == Round.Five){
            this.round = Round.One;
        }
        else{
            this.round = Round.values()[round.ordinal() + 1];
        }
    }

    public Turn getTurn() {
        return turn;
    }

    public void nextTurn(){
        if(this.turn == Turn.Five){
            this.turn = Turn.One;
            nextRound();
        }
        else{
            this.turn = Turn.values()[turn.ordinal() + 1];
        }
    }
}