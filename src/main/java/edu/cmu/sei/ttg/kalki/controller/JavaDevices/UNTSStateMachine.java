package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class UNTSStateMachine extends StateMachine {

    static {
        System.loadLibrary("untsfsm");
    }
    /*
    public static void main(String[] args) {
        UNTSStateMachine main = new UNTSStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }
     */

    /**
     * Constructor for DeviceStateMachine inherits from StateMachine
     * @param name  deviceName
     * @param id    deviceID
     */
    public UNTSStateMachine(String name, int id) {
        super(name, id);
    }

    /**
     * Native call to method generateNextState from untsfsm.c
     * Uses this.currentState and this.currentEvent
     */
    private native int generateNextState(String alertType, int newState);

    public void callNative(){

        setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));

    }

}