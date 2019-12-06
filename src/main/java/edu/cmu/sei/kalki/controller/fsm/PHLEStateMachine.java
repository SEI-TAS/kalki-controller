package edu.cmu.sei.kalki.controller.fsm;

import edu.cmu.sei.kalki.controller.StateMachine;

public class PHLEStateMachine extends StateMachine
{

    static {
        System.loadLibrary("phlefsm");
    }

    public PHLEStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}


