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

    /* Just for testing
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
    @Override
    public void run() {
        int previousState = this.getCurrentState();
        System.out.println("DLC pre gen: current state: " + previousState);
        this.generateNextState();
        System.out.println("DLC post gen: current state: " + this.getCurrentState());
        System.out.println("Posting new security state to Postgres");
        DeviceSecurityState newState = new DeviceSecurityState(this.getDeviceID(), this.getCurrentState());
        newState.insert();
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        thisDevice.setCurrentState(newState);
        if(previousState == 1 && this.getCurrentState()==2){
            doubleSamplingRate(thisDevice);
        }
        thisDevice.insertOrUpdate();
        System.out.println("Finished updating device security state");
    }

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
    private native void generateNextState();

}
