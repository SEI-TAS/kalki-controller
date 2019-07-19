package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class DLCStateMachine extends StateMachine {

    /*
     * Loads static library generated in build/libs folder
     */
    static {
        System.loadLibrary("dlcfsm");
    }
    /*
    public static void main(String[] args) {
        DLCStateMachine main = new DLCStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }
    */

    /**
     * Calls Native C code to generate new currentState
     * - C code contains synchronize statement on Java Obj
     * Publishes new currentState to Postgres Database
     */

    /**
     * Constructor for DeviceStateMachine inherits from StateMachine
     * @param name  deviceName
     * @param id    deviceID
     */
    public DLCStateMachine(String name, int id) {
        super(name, id);
    }

    /**
     * Native call to method generateNextState from dlcfsm.c
     * Uses this.currentState and this.currentEvent
     */
    private native int generateNextState(String alertType, int currentState);

    public void callNative(){
        System.out.println("Previous State: " + this.getCurrentState());
        int returnedState = this.generateNextState(this.getCurrentEvent(), this.getCurrentState());
        this.setCurrentState(returnedState);
        System.out.println("New State: " + this.getCurrentState());
        //Postgres calls to add new state to DB
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        DeviceSecurityState newState = new DeviceSecurityState(this.getDeviceID(), returnedState);
        newState.insert();
        thisDevice.setCurrentState(newState);
        thisDevice.insertOrUpdate();
    }

}
