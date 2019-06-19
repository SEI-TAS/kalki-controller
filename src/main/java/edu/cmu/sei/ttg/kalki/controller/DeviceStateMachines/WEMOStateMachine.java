package edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines;

//import edu.cmu.sei.ttg.kalki.database.Postgres;
//import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class WEMOStateMachine extends StateMachine {

    static {
        System.loadLibrary("wemofsm");
    }

    @Override
    public void run() {
        System.out.println("WEMO pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("WEMO post gen: current state: " + this.getCurrentState());
        // Uncomment this for running
        //Postgres.insertDeviceSecurityState(new DeviceSecurityState(this.getDeviceID(), this.getCurrentState()));
    }

    public WEMOStateMachine(String name, int id) { super(name, id);}

    private native void generateNextState();

}

