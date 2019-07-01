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

    void pushNewDLC(String deviceName, int deviceID){ dlcArrayList.add(new DLCStateMachine(deviceName, deviceID));}

    void pushNewUNTS(String deviceName, int deviceID){ untsArrayList.add(new UNTSStateMachine(deviceName, deviceID));}

    void pushNewPHLE(String deviceName, int deviceID){ phleArrayList.add(new PHLEStateMachine(deviceName, deviceID));}

    void pushNewWEMO(String deviceName, int deviceID){ wemoArrayList.add(new WEMOStateMachine(deviceName, deviceID));}

    DLCStateMachine queryForDLC(String deviceName){
        for(DLCStateMachine holder: dlcArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return new DLCStateMachine("empty", 99);
    }

    WEMOStateMachine queryForWEMO(String deviceName){
        for(WEMOStateMachine holder: wemoArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return new WEMOStateMachine("empty", 99);
    }

    UNTSStateMachine queryForUNTS(String deviceName){
        for(UNTSStateMachine holder: untsArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return new UNTSStateMachine("empty", 99);
    }

    PHLEStateMachine queryForPHLE(String deviceName){
        for(PHLEStateMachine holder: phleArrayList)
            if(holder.getName().equals(deviceName)){ return holder; }
        return new PHLEStateMachine("empty", 99);
    }
}
