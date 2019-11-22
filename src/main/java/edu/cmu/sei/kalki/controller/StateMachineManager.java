package edu.cmu.sei.kalki.controller;


import edu.cmu.sei.kalki.controller.fsm.DLCStateMachine;
import edu.cmu.sei.kalki.controller.fsm.PHLEStateMachine;
import edu.cmu.sei.kalki.controller.fsm.StateMachine;
import edu.cmu.sei.kalki.controller.fsm.UNTSStateMachine;
import edu.cmu.sei.kalki.controller.fsm.WEMOStateMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StateMachineManager
{
    private List<StateMachine> stateMachines;

    StateMachineManager() {
        this.stateMachines = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * pushNew* adds a statemachine object to an arraylist for access by the listener to push new states
     */
    void addStateMachine(StateMachine stateMachine){
        stateMachines.add(stateMachine);
    }

    /**
     *
     * @param deviceName name of device from Postgres Database
     * @param deviceID   ID of device from Postgres Database
     * @return returns a state machine for the given device type.
     */

    StateMachine getStateMachine(String deviceName, int deviceID, int currentState, int deviceTypeId){
        for(StateMachine stateMachine: stateMachines){
            if(deviceName.equals(stateMachine.getName()) && (stateMachine.getDeviceID() == deviceID)){
                return stateMachine;
            }
        }

        StateMachine newStateMachine = null;
        switch (deviceTypeId){
            case 1:
                newStateMachine = new DLCStateMachine(deviceName, deviceID, currentState);
                break;
            case 2:
                newStateMachine = new UNTSStateMachine(deviceName, deviceID, currentState);
                break;
            case 3:
                newStateMachine = new WEMOStateMachine(deviceName, deviceID, currentState);
                break;
            case 4:
                newStateMachine = new PHLEStateMachine(deviceName, deviceID, currentState);
                break;
        }

        if(newStateMachine != null)
        {
            this.addStateMachine(newStateMachine);
        }

        return newStateMachine;
    }
}
