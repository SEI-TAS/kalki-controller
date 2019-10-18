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
    public PHLEStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }

    /**
     * Native call to method generateNextState from phlefsm.c
     * Uses this.currentState and this.currentEvent
     */
    private native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

    public void callNative(int samplingRate, int defaultSamplingRate){
        while (this.getLockState()){
            try {
                TimeUnit.SECONDS.sleep(2);
            }
            catch (InterruptedException e ){
                e.printStackTrace();
            }
        }
        this.lock();
        System.out.println("Alert: " + this.getCurrentEvent() + " Previous State: " + this.getCurrentState());
        int[] results = this.generateNextState(this.getCurrentEvent(), this.getCurrentState(), samplingRate, defaultSamplingRate);
        this.setCurrentState(results[0]);
        System.out.println("Current State: " + this.getCurrentState());
        this.updateDevice(results[1]);
        this.unlock();
    }

}


