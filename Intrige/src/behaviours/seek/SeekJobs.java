package behaviours.seek;

import jade.core.behaviours.Behaviour;

public abstract class SeekJobs extends Behaviour {

    // Constructor
    public SeekJobs() {
    }

    // This method is called when the behavior starts
    public void onStart() {
        System.out.println("Starting seeking jobs");
    }

    // This method is called repeatedly until the behavior is finished
    public void action() {
        // TODO Receive state and re-send it to master after assigning? Or send it to the player?
        System.out.println("Performing seeking jobs");
    }

    // This method is called when the behavior is finished
    public int onEnd() {
        System.out.println("Ending seeking jobs");
        return 0;
    }

    // This method is called to determine whether the behavior is finished
    public boolean done() {
        return false; // This behavior never finishes
    }
}
