package config;

public class GameConfig {
    public static final int NUM_ROUNDS = 5;
    public static final int NUM_PLAYERS = 5;
    public static final int STARTING_MONEY = 32000;
    public static final int[] PALACE_CARD_VALUES = {1000, 3000, 6000, 10000};
    public static final int NUM_SCRIBES_PER_PLAYER = 2;
    public static final int NUM_MINISTERS_PER_PLAYER = 2;
    public static final int NUM_ALCHEMISTS_PER_PLAYER = 2;
    public static final int NUM_HEALERS_PER_PLAYER = 2;
    public static final int MINIMUM_BRIBE = 1000;
    public static long TIMEOUT = 5000;
    public static final int NUM_REPETITIONS = 5;
    public static final String CSV_FILENAME = "results10rounds20Players.csv";
    public static final String[] CSV_HEADERS = {"Player1", "Player2", "Player3", "Player4", "Player5"};
    public static final String[] AGENT_TYPES = {"agents.RandomAgent", "agents.GreedyAgent", "agents.TrustAgent"};
    public static long ACTION_DELAY_MS = 100;
}
