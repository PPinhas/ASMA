package game.conflict;

import game.Piece;
import game.Player;

import java.util.HashMap;
import java.util.TreeSet;

public abstract class Conflict {
    private final Piece.Job job;
    private final TreeSet<Player> players;
    private final HashMap<Player, Integer> bribes;

    public Conflict(Piece.Job job) {
        this.job = job;
        this.players = new TreeSet<>();
        this.bribes = new HashMap<>();
    }

    public Piece.Job getJob() {
        return job;
    }

    public TreeSet<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public HashMap<Player, Integer> getBribes() {
        return bribes;
    }

    public void addBribe(int playerId, int bribe) {
        Player player = null;
        for (Player p : players) {
            if (p.getId() == playerId) {
                player = p;
                break;
            }
        }

        if (player == null) {
            throw new IllegalArgumentException("Player with id " + playerId + " is not in this conflict");
        }
        bribes.put(player, bribe);
    }

    public Integer getBribe(Player player) {
        return bribes.get(player);
    }
}
