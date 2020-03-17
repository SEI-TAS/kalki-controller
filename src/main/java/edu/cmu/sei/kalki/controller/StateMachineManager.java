package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.models.Device;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class StateMachineManager
{
    private Map<Integer, StateMachine> stateMachines;

    /**
     * Initializes the map of machines.
     */
    StateMachineManager() {
        this.stateMachines = Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Gets or creates a new state machine for the given device.
     * @param device information about the device.
     * @return returns a state machine for the given device type.
     */
    StateMachine getStateMachine(Device device){
        if(stateMachines.containsKey(device.getId())) {
            return stateMachines.get(device.getId());
        }
        else {
            // If machine was not found, create it and store it.
            StateMachine newStateMachine = new StateMachine(device);
            stateMachines.put(device.getId(), newStateMachine);
            return newStateMachine;
        }
    }
}
