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
        System.out.println("inside random seekJobs");
        ArrayList<Piece> pieces = new ArrayList<>(intrigueAgent.getOwnPlayer().getPieces());
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        ArrayList<Player> players = new ArrayList<>(intrigueAgent.getGame().getPlayers());
        players.remove(intrigueAgent.getOwnPlayer());

        Random random = new Random();

        System.out.println("pieces: " + pieces);
        System.out.println("players: " + players);

        selectRandomIndex(pieces, pieceIndices, random);
        selectRandomIndex(players, playerIndices, random);



        // try second employee
        if (pieces.isEmpty()) return new EmployeesSent(pieceIndices, playerIndices);

        selectRandomIndex(pieces, pieceIndices, random);
        selectRandomIndex(players, playerIndices, random);

        System.out.println("pieces: " + pieces);
        System.out.println("pieceIndices: " + pieceIndices);
        System.out.println("playerIndices: " + playerIndices);

        return new EmployeesSent(pieceIndices, playerIndices);
    }

    private <T> void selectRandomIndex(ArrayList<T> list, ArrayList<Integer> indexList, Random random) {
        int index = random.nextInt(list.size());
        indexList.add(index);
        list.remove(index);
    }
}
