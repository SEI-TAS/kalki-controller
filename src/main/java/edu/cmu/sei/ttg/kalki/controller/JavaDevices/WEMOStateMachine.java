package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class WEMOStateMachine extends StateMachine {

    static {
        System.loadLibrary("wemofsm");
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

    public void callNative(){

        setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));

    }

}

