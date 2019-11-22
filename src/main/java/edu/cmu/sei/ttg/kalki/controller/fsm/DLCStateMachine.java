package edu.cmu.sei.ttg.kalki.controller.fsm;

public class DLCStateMachine extends StateMachine {

    static {
        System.loadLibrary("dlcfsm");
    }

    public DLCStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}
