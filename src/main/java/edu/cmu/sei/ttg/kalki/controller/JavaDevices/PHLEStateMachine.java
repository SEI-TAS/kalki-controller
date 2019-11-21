package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

public class PHLEStateMachine extends StateMachine {

    static {
        System.loadLibrary("phlefsm");
    }

    /**
     * Constructor for DeviceStateMachine inherits from StateMachine
     * @param name  deviceName
     * @param id    deviceID
     */
    public PHLEStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }

    /**
     * Native call to method generateNextState from phlefsm.c
     * Uses this.currentState and this.currentEvent
     */
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}


