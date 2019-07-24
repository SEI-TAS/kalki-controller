package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

import java.util.concurrent.TimeUnit;

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
        while (this.getLockState()){
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch (InterruptedException e ){
                e.printStackTrace();
            }
        }
        this.lock();
        int previousState = this.getCurrentState();
        System.out.println("Alert: " + this.getCurrentEvent() + " Previous State: " + this.getCurrentState());
        this.setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));
        System.out.println("Current State: " + this.getCurrentState());
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        if (this.getCurrentState()==2 && previousState==1){
            changeSampleRate(thisDevice);
        }
        DeviceSecurityState thisSecurityState = new DeviceSecurityState(this.getDeviceID(), this.getCurrentState());
        thisSecurityState.insert();
        thisDevice.setCurrentState(thisSecurityState);
        thisDevice.insertOrUpdate();
        this.unlock();
    }

}
