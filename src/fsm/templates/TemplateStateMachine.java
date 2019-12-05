package edu.cmu.sei.kalki.controller.fsm;

public class <NAME>StateMachine extends StateMachine {

    static {
        System.loadLibrary("<name>");
    }

    public <NAME>StateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}
