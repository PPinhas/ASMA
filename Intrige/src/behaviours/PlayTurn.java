package behaviours;

import jade.core.behaviours.Behaviour;

public class PlayTurn extends Behaviour {

    // Constructor
    public PlayTurn() {
    }

    // This method is called when the behavior starts
    public void onStart() {
        System.out.println("Starting PlayTurn");
    }

    // This method is called repeatedly until the behavior is finished
    public void action() {
        System.out.println("Performing PlayTurn");
    }

    // This method is called when the behavior is finished
    public int onEnd() {
        System.out.println("Ending PlayTurn");
        return 0;
    }

    // This method is called to determine whether the behavior is finished
    public boolean done() {
        return false; // This behavior never finishes
    }
}
