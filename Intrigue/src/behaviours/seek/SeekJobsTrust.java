package behaviours.seek;

import agents.TrustAgent;
import config.messages.EmployeesSent;

public class SeekJobsTrust extends SeekJobs {
    private final TrustAgent trustAgent;

    public SeekJobsTrust(TrustAgent trustAgent) {
        super(trustAgent);
        this.trustAgent = trustAgent;
    }

    @Override
    public EmployeesSent seekJobs() {
        // TODO choose the palaces based on the trust factor and pieces of the palace. Maybe consider current pieces on the palace/waiting?

        return new EmployeesSent(pieceIndices, playerIndices);
    }

}
