import game.Game;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

import java.util.ArrayList;

import static config.GameConfig.AGENT_TYPES;

public class Main {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true");
        profile.setParameter(Profile.CONTAINER_NAME, "Intrigue");
        AgentContainer gameContainer = jade.core.Runtime.instance().createMainContainer(profile);
        new Game(gameContainer);
=======
        for (int i = 0; i < AGENT_TYPES.length; i++) {
            for (int j = 0; j < AGENT_TYPES.length; j++) {
                for (int k = 0; k < AGENT_TYPES.length; k++) {
                    for (int l = 0; l < AGENT_TYPES.length; l++) {
                        for (int m = 0; k < AGENT_TYPES.length; m++) {
                            String[] agentTypes = {AGENT_TYPES[i], AGENT_TYPES[j], AGENT_TYPES[k], AGENT_TYPES[l], AGENT_TYPES[m]};
                            Profile profile = new ProfileImpl();
                            profile.setParameter(Profile.CONTAINER_NAME, "Intrigue");
                            AgentContainer gameContainer = jade.core.Runtime.instance().createMainContainer(profile);
                            new Game(gameContainer, true, agentTypes);

                            break;
                        }
                        break;
                    }
                    break;
                }
                break;
            }
            break;
        }
>>>>>>> Stashed changes
    }
}