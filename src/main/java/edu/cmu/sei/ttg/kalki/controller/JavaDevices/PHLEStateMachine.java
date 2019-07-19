package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class PHLEStateMachine extends StateMachine {

    static {
        System.loadLibrary("phlefsm");
    }
    /* Just for testing
    public static void main(String[] args) {
        PHLEStateMachine main = new PHLEStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }
     */

    /**
     * Constructor for DeviceStateMachine inherits from StateMachine
     * @param name  deviceName
     * @param id    deviceID
     */
    public PHLEStateMachine(String name, int id) {
        super(name, id);
    }

    /**
     * Native call to method generateNextState from phlefsm.c
     * Uses this.currentState and this.currentEvent
     */
    private native int generateNextState(String alertType, int newState);

    public void callNative(){

        setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));

    }

}


