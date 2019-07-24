package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.models.Device;

public class StateMachine {

    private String deviceName; //name of device received from database

    private int deviceID; //ID of device received from database

    private int currentState; //the current state of device inits to zero and is changed by native calls

    private String currentEvent; //the most recent alert-type associated with the device

    private boolean threadLock = false;

    /**
     * Constructor for StateMachine
     * @param name
     * @param ID
     */
    public StateMachine(String name, int ID) {
        this.deviceName = name;
        this.deviceID = ID;
        this.currentState = 1; //default to normal
    }

    /**
     * @return returns device ID
     */
    public int getDeviceID() {
        return this.deviceID;
    }

    /**
     * @return returns device name
     */
    public String getDeviceName() {
        return this.deviceName;
    }

    /**
     * @return returns currentState
     */
    int getCurrentState() {
        return this.currentState;
    }
    
    String getCurrentEvent(){ return this.currentEvent; }

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

    static void changeSampleRate(Device deviceIn){
        int samplingRate = deviceIn.getSamplingRate();
        deviceIn.setSamplingRate(samplingRate*2);
    }
}
