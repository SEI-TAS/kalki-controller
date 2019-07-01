package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

public class StateMachine implements Runnable {

    private String deviceName;

    private int deviceID;

    private int currentState;

    private String currentEvent;

    public String getName() {
        return this.deviceName;
    }

    public void setEvent(String newEvent) {
        this.currentEvent = newEvent;
    }

    protected void printStateMachine() {
        System.out.println(
                " Name: " + this.deviceName +
                        " current state: " + this.currentState +
                        " event: " + this.currentEvent +
                        " deviceID: " + this.deviceID);
    }

    @Override
    public void run() {
        return;
    }

    public StateMachine(String name, int ID) {
        this.deviceName = name;
        this.deviceID = ID;
    }

    public int getDeviceID() {
        return this.deviceID;
    }

    public String getDeviceName() {
        return this.deviceName;
    }

    public int getCurrentState() {
        return this.currentState;
    }

}
