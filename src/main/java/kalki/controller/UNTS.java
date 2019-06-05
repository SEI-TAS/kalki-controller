package kalki.controller;

public class UNTS implements Runnable{
    static {
        System.loadLibrary("fsm");
    }
    static {
        System.loadLibrary("fsm");
    }
    private String deviceName;
    //Init current state to 0;
    private int currentState = 0;

    private int currentEvent = 0;

    @Override
    public void run() {
        this.generateNextState();
    }
    public UNTS(String name) { deviceName = name; }

    private native void generateNextState();

    public String getName() {
        return this.deviceName;
    }

    public void setEvent(int event) {
        this.currentEvent = event;
        System.out.println("New Event = " + this.currentEvent);
    }
    public void deviceString() { System.out.println("Device Name: " + this.deviceName + " current state: " + this.currentState); }
}