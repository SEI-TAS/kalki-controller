package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class WEMOStateMachine extends StateMachine {

    static {
        System.loadLibrary("wemofsm");
    }

    public static void main(String[] args) {
        WEMOStateMachine main = new WEMOStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }

    /**
     * Calls Native C code to generate new currentState
     * - C code contains synchronize statement on Java Obj
     * Publishes new currentState to Postgres Database
     */
    @Override
    public void run() {
        System.out.println("WEMO pre gen: current state: " + this.getCurrentState());
        this.setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));
        System.out.println("WEMO post gen: current state: " + this.getCurrentState());
        // Uncomment this for running
    }

    /**
     * Constructor for DeviceStateMachine inherits from StateMachine
     * @param name  deviceName
     * @param id    deviceID
     */
    public WEMOStateMachine(String name, int id) {
        super(name, id);
    }

    /**
     * Native call to method generateNextState from wemofsm.c
     * Uses this.currentState and this.currentEvent
     */
    private native int generateNextState(String alertType, int newState);

}

