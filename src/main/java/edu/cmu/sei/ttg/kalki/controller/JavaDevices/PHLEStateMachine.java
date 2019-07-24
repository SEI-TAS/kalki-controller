package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

import java.util.concurrent.TimeUnit;

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
        System.out.println("Alert: " + this.getCurrentEvent() + " Previous State: " + previousState);
        this.setCurrentState(this.generateNextState(this.getCurrentEvent(), this.getCurrentState()));
        System.out.println("Current State: " + this.getCurrentState());
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        if (this.getCurrentState()==2 && previousState == 1){
            changeSampleRate(thisDevice);
        }
        DeviceSecurityState thisSecurityState = new DeviceSecurityState(this.getDeviceID(), this.getCurrentState());
        thisSecurityState.insert();
        thisDevice.setCurrentState(thisSecurityState);
        thisDevice.insertOrUpdate();
        this.unlock();
    }

}


