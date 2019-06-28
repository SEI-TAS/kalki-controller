package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.controller.JavaDevices.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class IOTController implements InsertHandler {

    DeviceManager deviceManager = new DeviceManager();

    public static void main(String[] args) {
        IOTController mainController = new IOTController();
        mainController.initializeDatabase();
        mainController.initListeners(mainController);
        Device testDevice;
        DeviceStatus testStatus;
        Postgres.insertGroup(new Group(10, "TestDevices"));
        for(int i = 1; i < 5; i++){
            testDevice = new Device("test_device", "its a "+i,
                    i, 10, "0.0.0.0", 5, 5);
            testDevice.insert();
            wait(2);
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            wait(2);
            for(int j = 1; j < 6; j++){
                Alert testAlert = new Alert("new-alert", testStatus.getId(), j);
                testAlert.insert();
                wait(2);
            }
        }
        System.exit(23);
    }
    @Override
    public void handleNewInsertion(int id) {
        System.out.println("Here in handler");
        Alert receivedAlert = Postgres.findAlert(id);
        if (receivedAlert == null) {
            System.out.println("alert not found");
        } else {
            System.out.println("alert found");
            //find device type
            Device foundDevice = Postgres.findDeviceByAlert(receivedAlert);
            String deviceName = foundDevice.getName();
            int deviceID = foundDevice.getId();
            int deviceTypeID = foundDevice.getType().getId();
            int alertTypeID = receivedAlert.getAlertTypeId();
            String alertTypeName = Postgres.findAlertType(alertTypeID).getName();
            System.out.println("Alert Type Name: " + alertTypeName);
            Thread opThread;
            switch (deviceTypeID) {
                case 1:
                    System.out.println("pushing new state to DLC: " + deviceName);
                    deviceManager.pushNewDLC(deviceName, deviceID);
                    DLCStateMachine dlc = deviceManager.queryForDLC(deviceName);
                    dlc.setEvent(alertTypeName);
                    opThread = new Thread(dlc);
                    break;
                case 2:
                    System.out.println("pushing new state to UNTS: " + deviceName);
                    deviceManager.pushNewUNTS(deviceName, deviceID);
                    UNTSStateMachine unts = deviceManager.queryForUNTS(deviceName);
                    unts.setEvent(alertTypeName);
                    opThread = new Thread(unts);
                    break;
                case 3:
                    System.out.println("pushing new state to WEMO: " + deviceName);
                    deviceManager.pushNewWEMO(deviceName, deviceID);
                    WEMOStateMachine wemo = deviceManager.queryForWEMO(deviceName);
                    wemo.setEvent(alertTypeName);
                    opThread = new Thread(wemo);
                    break;
                case 4:
                    System.out.println("pushing new state to PHLE: " + deviceName);
                    deviceManager.pushNewPHLE(deviceName, deviceID);
                    PHLEStateMachine phle = deviceManager.queryForPHLE(deviceName);
                    phle.setEvent(alertTypeName);
                    opThread = new Thread(phle);
                    break;
                default:
                    opThread = new Thread(new StateMachine("empty", 0));
                    break;
            }
            opThread.start();
        }
    }

    void initializeDatabase(){
        Postgres.setLoggingLevel(Level.WARNING);
        Postgres.initialize("0.0.0.0", "5432", "kalkidb" , "kalkiuser", "kalkipass");
        Postgres.resetDatabase();

    }

    IOTController(){

    }

    void initListeners(InsertHandler alertHandler){
        InsertListener.startUpListener("alerthistoryinsert", alertHandler);
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch(InterruptedException e){
            System.out.println("caught exception in init listeners");
        }

    }
    static void wait(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (InterruptedException e){
            System.out.println("Error in wait");
        }
    }

}
