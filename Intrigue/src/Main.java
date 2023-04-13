import config.GameExporter;
import game.Game;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import java.util.ArrayList;

import static config.GameConfig.*;
import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Integer[] values = {0,0,0,0,0};
        for(int i =0; i < values.length; i = i+NUM_PLAYERS){
            String[] agentTypes = {
                    AGENT_TYPES[values[i]], AGENT_TYPES[values[i+1]], AGENT_TYPES[values[i+2]], AGENT_TYPES[values[i+3]], AGENT_TYPES[values[i+4]]
            };
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
}