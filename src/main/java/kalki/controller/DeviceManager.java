package kalki.controller;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.models.Alert;

import java.util.ArrayList;


public class DeviceManager{

    private ArrayList<DLC> dlcArrayList;
    private ArrayList<PHLE> phleArrayList;
    private ArrayList<UNTS> untsArrayList;
    private ArrayList<WEMO> wemoArrayList;
    DeviceManager() {
        this.dlcArrayList = new ArrayList<>();
        this.phleArrayList = new ArrayList<>();
        this.untsArrayList = new ArrayList<>();
        this.wemoArrayList = new ArrayList<>();
    }

    public Object queryForDevice(String deviceName, String deviceType){
        switch (deviceType){
            case "WEMO":
                for(WEMO holder: wemoArrayList)
                    if(holder.getName().equals(deviceName)){ return holder; }
                return null;
            case "UNTS":
                for(UNTS holder: untsArrayList)
                    if(holder.getName().equals(deviceName)){ return holder; }
                return null;
            case "DLC":
                for(DLC holder: dlcArrayList)
                    if(holder.getName().equals(deviceName)){ return holder; }
                return null;
            case "PHLE":
                for(PHLE holder: phleArrayList)
                    if(holder.getName().equals(deviceName)){ return holder; }
                return null;
            default:
                return null;
        }
    }
    public void pushNewDLC(String deviceID){ dlcArrayList.add(new DLC(deviceID));}
    public void pushNewUNTS(String deviceID){ untsArrayList.add(new UNTS(deviceID));}
    public void pushNewPHLE(String deviceID){ phleArrayList.add(new PHLE(deviceID));}
    public void pushNewWEMO(String deviceID){ wemoArrayList.add(new WEMO(deviceID));}

}
