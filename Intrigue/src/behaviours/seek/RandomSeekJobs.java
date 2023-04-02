package behaviours.seek;

import agents.IntrigueAgent;
import config.messages.EmployeesSent;
import game.Piece;
import game.Player;

import java.util.ArrayList;
import java.util.Random;

public class RandomSeekJobs extends SeekJobs {

    public RandomSeekJobs(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    public EmployeesSent seekJobs() {
        ArrayList<Piece> pieces = new ArrayList<>(intrigueAgent.getOwnPlayer().getPieces());
        ArrayList<Integer> pieceIndices = new ArrayList<>();
        ArrayList<Integer> playerIndices = new ArrayList<>();
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        ArrayList<Player> players = new ArrayList<>(intrigueAgent.getGame().getPlayers());
        players.remove(intrigueAgent.getOwnPlayer());

        Random random = new Random();
        selectRandomIndex(pieces, pieceIndices, random);
        selectRandomIndex(players, playerIndices, random);

        // try second employee
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        selectRandomIndex(pieces, pieceIndices, random);
        selectRandomIndex(players, playerIndices, random);

        return new EmployeesSent(pieceIndices, playerIndices);
    }

    private <T> void selectRandomIndex(ArrayList<T> list, ArrayList<Integer> indexList, Random random) {
        int index = random.nextInt(list.size());
        indexList.add(index);
        list.remove(index);
    }
}
