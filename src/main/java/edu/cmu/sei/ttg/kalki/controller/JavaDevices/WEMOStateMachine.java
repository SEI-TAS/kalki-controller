package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

public class WEMOStateMachine extends StateMachine {

    static {
        System.loadLibrary("wemofsm");
    }

    public WEMOStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}

