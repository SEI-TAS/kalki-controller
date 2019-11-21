package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;
import edu.cmu.sei.ttg.kalki.models.StageLog;

import java.util.concurrent.TimeUnit;

public class StateMachine {

    private String deviceName; //name of device received from database

    private int deviceID; //ID of device received from database

    private int currentState; //the current state of device inits to zero and is changed by native calls

    private String currentEvent; //the most recent alert-type associated with the device

    private boolean threadLock = false;

    //constructor of base statemachine
    StateMachine(String name, int ID, int currentState) {
        this.deviceName = name;
        this.deviceID = ID;
        this.currentState = currentState; //default to normal
    }

    /**
     * @return returns device ID
     */
    public int getDeviceID() {
        return this.deviceID;
    }

    /**
     * @return returns currentState
     */
    int getCurrentState() {
        return this.currentState;
    }

    /**
     * @return returns currentevent
     */
    String getCurrentEvent(){ return this.currentEvent; }

    /**
     * Sets the currentState of the device
     */
    void setCurrentState(int newState){ this.currentState = newState; }

    /**
     * @return returns the name of the device
     */
    public String getName() {
        return this.deviceName;
    }

    /**
     * @param newEvent this is the latest alert-type received from listener given by the handler
     */
    public void setEvent(String newEvent) {
        System.out.println("Here setting event: "+ newEvent);
        this.currentEvent = newEvent;
    }

    public String getEvent(){ return this.currentEvent; }

    void unlock(){ threadLock = false; }

    void lock() { threadLock = true; }

    synchronized boolean getLockState(){
        return this.threadLock;
    }

    void updateDevice(int newSamplingRate){
        Device thisDevice = Postgres.findDevice(this.getDeviceID());
        DeviceSecurityState thisSecurityState = new DeviceSecurityState(this.getDeviceID(), this.getCurrentState());
        thisSecurityState.insert();
        thisDevice.setCurrentState(thisSecurityState);
        thisDevice.setSamplingRate(newSamplingRate);
        thisDevice.insertOrUpdate();
        System.out.println("Sampling Rate:" + newSamplingRate);
        StageLog log = new StageLog(thisDevice.getCurrentState().getId(), StageLog.Action.OTHER, StageLog.Stage.TRIGGER, "Updated device:"+thisDevice.getId());
        log.insert();
    }

    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

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
