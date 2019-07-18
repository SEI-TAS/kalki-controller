package edu.cmu.sei.ttg.kalki.controller;


import edu.cmu.sei.ttg.kalki.controller.JavaDevices.DLCStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.PHLEStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.UNTSStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.WEMOStateMachine;

import java.util.ArrayList;


class DeviceManager {

    private ArrayList<DLCStateMachine> dlcArrayList;
    private ArrayList<PHLEStateMachine> phleArrayList;
    private ArrayList<UNTSStateMachine> untsArrayList;
    private ArrayList<WEMOStateMachine> wemoArrayList;

    DeviceManager() {
        this.dlcArrayList = new ArrayList<>();
        this.phleArrayList = new ArrayList<>();
        this.untsArrayList = new ArrayList<>();
        this.wemoArrayList = new ArrayList<>();
    }

    /**
     *
     * @param deviceName name of device from postgres database
     * @param deviceID   ID of device from Postgres database
     * pushNew* adds a statemachine object to an arraylist for access by the listener to push new states
     *
     */
    void pushNewDLC(String deviceName, int deviceID){
        if(queryForDLC(deviceName, deviceID).getDeviceName().equals("empty")){
            dlcArrayList.add(new DLCStateMachine(deviceName, deviceID));
        }
        /*else {
            System.out.println("device already exists... returning");
        } */
    }

    void pushNewUNTS(String deviceName, int deviceID){
        if(queryForUNTS(deviceName,deviceID).getDeviceName().equals("empty")){
            untsArrayList.add(new UNTSStateMachine(deviceName, deviceID));
        }
        /*else {
            System.out.println("device already exists... returning");
        } */
    }

    void pushNewPHLE(String deviceName, int deviceID){
        if(queryForPHLE(deviceName, deviceID).getDeviceName().equals("empty")){
            phleArrayList.add(new PHLEStateMachine(deviceName, deviceID));
        }
        /*else{
            System.out.println("device already exists... returning");
        } */
    }

    void pushNewWEMO(String deviceName, int deviceID){
        if(queryForWEMO(deviceName, deviceID).getDeviceName().equals("empty")){
            wemoArrayList.add(new WEMOStateMachine(deviceName, deviceID));
        }
        else{
            System.out.println("device already exists... returning");
        }
    }

    /**
     *
     * @param deviceName name of device from Postgres Database
     * @param deviceID   ID of device from Postgres Database
     * @return returns the found device is one exists and returns an empty device handled by the listener
     * queryFor* returns the device object with the correct name and deviceId given for use by the listener
     */

    DLCStateMachine queryForDLC(String deviceName, int deviceID){
        for(DLCStateMachine holder: dlcArrayList)
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        //else return an empty device which will trigger nothing
        return new DLCStateMachine("empty", 99);
    }

    WEMOStateMachine queryForWEMO(String deviceName, int deviceID){
        for(WEMOStateMachine holder: wemoArrayList)
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        //else return an empty device which will trigger nothing
        return new WEMOStateMachine("empty", 99);
    }

    UNTSStateMachine queryForUNTS(String deviceName, int deviceID){
        for(UNTSStateMachine holder: untsArrayList)
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        //else return an empty device which will trigger nothing
        return new UNTSStateMachine("empty", 99);
    }

    PHLEStateMachine queryForPHLE(String deviceName, int deviceID){
        for(PHLEStateMachine holder: phleArrayList)
            if(holder.getName().equals(deviceName) && holder.getDeviceID()==deviceID){ return holder; }
        //else return an empty device which will trigger nothing
        return new PHLEStateMachine("empty", 99);
    }
}
