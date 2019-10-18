package edu.cmu.sei.ttg.kalki.controller;


import edu.cmu.sei.ttg.kalki.controller.JavaDevices.DLCStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.PHLEStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.UNTSStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.WEMOStateMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


class DeviceManager {

    private List<DLCStateMachine> dlcArrayList;
    private List<PHLEStateMachine> phleArrayList;
    private List<UNTSStateMachine> untsArrayList;
    private List<WEMOStateMachine> wemoArrayList;

    DeviceManager() {
        this.dlcArrayList = Collections.synchronizedList(new ArrayList<>());
        this.phleArrayList = Collections.synchronizedList(new ArrayList<>());
        this.untsArrayList = Collections.synchronizedList(new ArrayList<>());
        this.wemoArrayList = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * pushNew* adds a statemachine object to an arraylist for access by the listener to push new states
     */
    void pushNewDLC(DLCStateMachine dlc){
        dlcArrayList.add(dlc);
    }

    void pushNewUNTS(UNTSStateMachine unts){
        untsArrayList.add(unts);
    }

    void pushNewPHLE(PHLEStateMachine phle){
        phleArrayList.add(phle);
    }

    void pushNewWEMO(WEMOStateMachine wemo){
        wemoArrayList.add(wemo);
    }

    /**
     *
     * @param deviceName name of device from Postgres Database
     * @param deviceID   ID of device from Postgres Database
     * @return returns the found device is one exists and returns an empty device handled by the listener
     * queryFor* returns the device object with the correct name and deviceId given for use by the listener
     */

    DLCStateMachine queryForDLC(String deviceName, int deviceID, int currentState){
        for(DLCStateMachine holder: dlcArrayList){
            if(deviceName.equals(holder.getName()) && (holder.getDeviceID() == deviceID)){ return holder; }
        }
        DLCStateMachine newDLC = new DLCStateMachine(deviceName, deviceID, currentState);
        this.pushNewDLC(newDLC);
        return newDLC;

    }

    WEMOStateMachine queryForWEMO(String deviceName, int deviceID, int currentState){
        for(WEMOStateMachine holder: wemoArrayList){
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        }
        WEMOStateMachine newWEMO = new WEMOStateMachine(deviceName, deviceID, currentState);
        this.pushNewWEMO(newWEMO);
        return newWEMO;
    }

    UNTSStateMachine queryForUNTS(String deviceName, int deviceID, int currentState){
        for(UNTSStateMachine holder: untsArrayList){
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }

        }
        UNTSStateMachine newUNTS = new UNTSStateMachine(deviceName, deviceID, currentState);
        this.pushNewUNTS(newUNTS);
        return newUNTS;

    }

    PHLEStateMachine queryForPHLE(String deviceName, int deviceID, int currentState){
        for(PHLEStateMachine holder: phleArrayList){
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        }
        PHLEStateMachine newPHLE = new PHLEStateMachine(deviceName, deviceID, currentState);
        this.pushNewPHLE(newPHLE);
        return newPHLE;
    }
}
