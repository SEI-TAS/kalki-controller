package edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class UNTSStateMachine extends StateMachine {

    static {
        System.loadLibrary("untsfsm");
    }

    @Override
    public void run() {
        System.out.println("UNTS pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("UNTS post gen: current state: " + this.getCurrentState());
        Postgres.insertDeviceSecurityState(new DeviceSecurityState(this.getDeviceID(), this.getCurrentState()));
    }

    public UNTSStateMachine(String name, int id) { super(name, id);}

    private native void generateNextState();

}