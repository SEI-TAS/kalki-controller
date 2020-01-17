package edu.cmu.sei.kalki.controller.fsm;

import edu.cmu.sei.kalki.controller.StateMachine;

public class DLinkCameraStateMachine extends StateMachine
{

    static {
        System.loadLibrary("dlcfsm");
    }

    public DLinkCameraStateMachine(String name, int id, int currentState) {
        super(name, id, currentState);
    }
    protected native int[] generateNextState(String alertType, int newState, int samplingRate, int defaultSamplingRate);

}
