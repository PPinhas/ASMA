import intrigue.game.Game;
public class Main {
    public static void main(String[] args) {
        Game game = new Game();

        do {game.playRound();} while (game.isOver() == false);
    }
}