import config.GameExporter;
import game.Game;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import java.util.ArrayList;

import static config.GameConfig.AGENT_TYPES;
import static config.GameConfig.NUM_REPETITIONS;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        String[] agentTypes = {AGENT_TYPES[0], AGENT_TYPES[0], AGENT_TYPES[0], AGENT_TYPES[0], AGENT_TYPES[0]};
        new GameExporter(agentTypes);
        for (int numRepetitions = 0; numRepetitions < NUM_REPETITIONS; numRepetitions++) {
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.CONTAINER_NAME, "Intrigue");
            AgentContainer gameContainer = jade.core.Runtime.instance().createMainContainer(profile);
            new Game(gameContainer, true, agentTypes);
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}