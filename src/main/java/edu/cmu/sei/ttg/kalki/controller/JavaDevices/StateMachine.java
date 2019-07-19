package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

public class StateMachine {

    private String deviceName; //name of device received from database

    private int deviceID; //ID of device received from database

    private int currentState; //the current state of device inits to zero and is changed by native calls

    private String currentEvent; //the most recent alert-type associated with the device

    /**
     * @return returns the name of the device
     */
    public String getName() {
        return this.deviceName;
    }

    /**
     * @param newEvent this is the latest alert-type received from listener given by the handler
     */
    public synchronized void setEvent(String newEvent) {
        this.currentEvent = newEvent;
    }

    //Prints the variables associated with the state machine
    protected void printStateMachine() {
        System.out.println(
                " Name: " + this.deviceName +
                        " current state: " + this.currentState +
                        " event: " + this.currentEvent +
                        " deviceID: " + this.deviceID);
    }


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
    synchronized int getCurrentState() {
        return this.currentState;
    }

    String getCurrentEvent(){ return this.currentEvent; }

    synchronized void setCurrentState(int newState){ this.currentState = newState; }
}
