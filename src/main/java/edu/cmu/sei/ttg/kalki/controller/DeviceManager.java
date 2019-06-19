package edu.cmu.sei.ttg.kalki.controller;
import edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines.DLCStateMachine;
import edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines.PHLEStateMachine;
import edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines.UNTSStateMachine;
import edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines.WEMOStateMachine;
import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceType;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


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
