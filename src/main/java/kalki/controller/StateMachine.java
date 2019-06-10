package kalki.controller;

import javax.swing.plaf.nimbus.State;

public class StateMachine implements Runnable{

    private String deviceName;

    private int currentState;

    private int currentEvent;

    protected String getName(){
        return this.deviceName;
    }
    protected void setEvent(int Event){
        this.currentEvent = Event;
    }
    protected void printStateMachine(){
        System.out.println(
                " Name: "+ this.deviceName +
                " current state: "+ this.currentState +
                " event: " + this.currentEvent);
    }
    @Override
    public void run() {
        return;
    }
    StateMachine(String name){
        this.deviceName = name;
    }
}
