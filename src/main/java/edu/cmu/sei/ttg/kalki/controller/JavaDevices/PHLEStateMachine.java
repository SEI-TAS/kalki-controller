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
     * Calls Native C code to generate new currentState
     * - C code contains synchronize statement on Java Obj
     * Publishes new currentState to Postgres Database
     */
    @Override
    public void run() {
        System.out.println("PHLE pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("PHLE post gen: current state: " + this.getCurrentState());
        //uncomment this for running
        DeviceSecurityState newState = new DeviceSecurityState(this.getDeviceID(), this.getCurrentState());
        newState.insert();
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        thisDevice.setCurrentState(newState);
        thisDevice.insertOrUpdate();
        System.out.println("Finished updating device security state");
    }
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
    private native void generateNextState();

}


