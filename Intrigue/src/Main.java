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
        Integer[] values = {
                1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
                2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
                1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,
                2,2,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0,0,0,
                2,2,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,
        };
        for(int i =0; i < values.length; i = i+NUM_PLAYERS){
            String[] agentTypes = {
                    AGENT_TYPES[values[i]], AGENT_TYPES[values[i+1]], AGENT_TYPES[values[i+2]], AGENT_TYPES[values[i+3]], AGENT_TYPES[values[i+4]],
                    AGENT_TYPES[values[i+5]], AGENT_TYPES[values[i+6]], AGENT_TYPES[values[i+7]], AGENT_TYPES[values[i+8]], AGENT_TYPES[values[i+9]],
                    AGENT_TYPES[values[i+10]], AGENT_TYPES[values[i+11]], AGENT_TYPES[values[i+12]], AGENT_TYPES[values[i+13]], AGENT_TYPES[values[i+15]],
                    AGENT_TYPES[values[i+15]], AGENT_TYPES[values[i+16]], AGENT_TYPES[values[i+17]], AGENT_TYPES[values[i+18]], AGENT_TYPES[values[i+19]]


            };
            new GameExporter(agentTypes);
            for (int numRepetitions = 0; numRepetitions < NUM_REPETITIONS; numRepetitions++) {
                Profile profile = new ProfileImpl();
                profile.setParameter(Profile.CONTAINER_NAME, "Intrigue");
                AgentContainer gameContainer = jade.core.Runtime.instance().createMainContainer(profile);
                new Game(gameContainer, true, agentTypes);
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}