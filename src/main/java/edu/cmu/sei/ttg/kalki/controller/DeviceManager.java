package edu.cmu.sei.ttg.kalki.controller;


import edu.cmu.sei.ttg.kalki.controller.JavaDevices.DLCStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.PHLEStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.UNTSStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.WEMOStateMachine;

import java.util.ArrayList;


public class DeviceManager {

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


    public void pushNewDLC(String deviceName, int deviceID){ dlcArrayList.add(new DLCStateMachine(deviceName, deviceID));}

    public void pushNewUNTS(String deviceName, int deviceID){ untsArrayList.add(new UNTSStateMachine(deviceName, deviceID));}

    public void pushNewPHLE(String deviceName, int deviceID){ phleArrayList.add(new PHLEStateMachine(deviceName, deviceID));}

    public void pushNewWEMO(String deviceName, int deviceID){ wemoArrayList.add(new WEMOStateMachine(deviceName, deviceID));}

    public DLCStateMachine queryForDLC(String deviceName){
        for(DLCStateMachine holder: dlcArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return null;
    }

    public WEMOStateMachine queryForWEMO(String deviceName){
        for(WEMOStateMachine holder: wemoArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return null;
    }

    public UNTSStateMachine queryForUNTS(String deviceName){
        for(UNTSStateMachine holder: untsArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return null;
    }

    public PHLEStateMachine queryForPHLE(String deviceName){
        for(PHLEStateMachine holder: phleArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return null;
    }
}
