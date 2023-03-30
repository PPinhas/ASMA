import game.Game;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;

public class Main {
    public static void main(String[] args) {

        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.GUI, "true");
        profile.setParameter(Profile.CONTAINER_NAME, "Intrige");
        AgentContainer gameContainer = jade.core.Runtime.instance().createMainContainer(profile);
        new Game(gameContainer, 5, 5);
    }
}