package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

import java.util.concurrent.TimeUnit;

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

