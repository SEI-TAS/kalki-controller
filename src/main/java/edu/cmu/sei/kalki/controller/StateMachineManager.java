package edu.cmu.sei.kalki.controller;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class StateMachineManager
{
    private static final String FSM_PACKAGE = "edu.cmu.sei.kalki.controller.fsm.";

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

    StateMachine getStateMachine(String deviceName, int deviceID, int currentState, String deviceTypeName){
        for(StateMachine stateMachine: stateMachines){
            if(deviceName.equals(stateMachine.getName()) && (stateMachine.getDeviceID() == deviceID)){
                return stateMachine;
            }
        }

        // If machine was not found, create it and store it.
        StateMachine newStateMachine = createStateMachine(deviceName, deviceID, currentState, deviceTypeName);
        if(newStateMachine != null)
        {
            this.addStateMachine(newStateMachine);
        }
        return newStateMachine;
    }

    private StateMachine createStateMachine(String deviceName, int deviceID, int currentState, String deviceTypeName) {
        StateMachine newStateMachine = null;
        try {
            // Ensure there are no spaces.
            deviceTypeName = deviceTypeName.replaceAll(" ", "");

            String classPath = FSM_PACKAGE + deviceTypeName + "StateMachine";
            Constructor con = Class.forName(classPath).getConstructor(String.class, Integer.TYPE, Integer.TYPE);
            newStateMachine = (StateMachine) con.newInstance(deviceName, deviceID, currentState);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error loading FSM: " + e.getMessage());
        }

        return newStateMachine;
    }
}
