package behaviours.bribe;

import jade.core.behaviours.Behaviour;

public abstract class ResolveConflict extends Behaviour {

    // Constructor
    public ResolveConflict() {
    }

    // This method is called when the behavior starts
    public void onStart() {
        System.out.println("Starting resolve conflict");
    }

    // This method is called repeatedly until the behavior is finished
    public void action() {
        // TODO Negotiate with other players directly? Seems wrong to use GameMaster intermediate
        System.out.println("Performing resolve conflict");
    }

    // This method is called when the behavior is finished
    public int onEnd() {
        System.out.println("Ending resolve conflict");
        return 0;
    }

    // This method is called to determine whether the behavior is finished
    public boolean done() {
        return false; // This behavior never finishes
    }
}
