package behaviours.seek;

import agents.IntrigueAgent;
import config.messages.EmployeesSent;
import game.Piece;
import game.Player;

import java.util.ArrayList;
import java.util.Random;

public class SeekJobsRandom extends SeekJobs {

    public SeekJobsRandom(IntrigueAgent intrigueAgent) {
        super(intrigueAgent);
    }

    @Override
    public EmployeesSent seekJobs() {
        ArrayList<Piece> pieces = new ArrayList<>(intrigueAgent.getOwnPlayer().getPieces());
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        ArrayList<Player> players = new ArrayList<>(intrigueAgent.getGame().getPlayers());
        players.remove(intrigueAgent.getOwnPlayer());

        Random random = new Random();
        selectRandomIndex(pieces, intrigueAgent.getOwnPlayer().getPieces(), pieceIndices, random);
        selectRandomIndex(players, intrigueAgent.getGame().getPlayers(), playerIndices, random);

        // try second employee
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        selectRandomIndex(pieces, intrigueAgent.getOwnPlayer().getPieces(), pieceIndices, random);
        selectRandomIndex(players, intrigueAgent.getGame().getPlayers(), playerIndices, random);

        return new EmployeesSent(pieceIndices, playerIndices);
    }

    private <T> void selectRandomIndex(ArrayList<T> availableList, ArrayList<T> realList, ArrayList<Integer> indexList, Random random) {
        int index = random.nextInt(availableList.size());
        indexList.add(realList.indexOf(availableList.get(index)));
        availableList.remove(index);
    }
}
