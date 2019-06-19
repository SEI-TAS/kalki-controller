package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.controller.DeviceStateMachines.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


public class IOTController implements InsertHandler {

    DeviceManager deviceManager = new DeviceManager();

    public static void main(String[] args) {
        IOTController mainController = new IOTController();
        mainController.initializeDatabase();
        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e) {
            System.out.println("Caught interrupt in Main");
        }
        mainController.initListeners(mainController);
        Postgres.insertGroup(new Group(11, "TestDLCDeviceAdd"));
        Device test_dlc0 = new Device("DLC00", "its a dlc",
                1, 11, "0.0.0.0", 5, 5);
        test_dlc0.insert();
        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e) {
            System.out.println("Caught interrupt in Main");
        }
        DeviceStatus dlcStatus = new DeviceStatus(test_dlc0.getId());
        dlcStatus.insert();
        try {
            TimeUnit.SECONDS.sleep(2);
        }
        catch(InterruptedException e) {
            System.out.println("Caught interrupt in Main");
        }
        Alert dlcTestAlert = new Alert("dlc-brute-force", dlcStatus.getId(), 1);
        dlcTestAlert.insert();
    }
    @Override
    public void handleNewInsertion(int id) {
        System.out.println("Here in handler");
        try {
            Alert receivedAlert = Postgres.findAlert(id).thenApplyAsync(alert->{return alert;}).toCompletableFuture().get();
            if(receivedAlert == null){
                System.out.println("alert not found");
            }
            else {
                System.out.println("alert found");
                //find device type
                Device foundDevice = Postgres.findDeviceByAlert(receivedAlert);
                String deviceName = foundDevice.getName();
                int deviceID = foundDevice.getId();
                int deviceTypeID = foundDevice.getType().getId();
                int alertTypeID = receivedAlert.getAlertTypeId();
                System.out.println("Alert Type ID: "+ alertTypeID);
                Thread opThread;
                switch(deviceTypeID){
                    case 1:
                        System.out.println("pushing new state to DLC: " + deviceName);
                        deviceManager.pushNewDLC(deviceName, deviceID);
                        DLCStateMachine dlc = deviceManager.queryForDLC(deviceName);
                        dlc.setEvent(alertTypeID);
                        opThread = new Thread(dlc);
                        break;
                    case 2:
                        System.out.println("pushing new state to UNTS: " + deviceName);
                        deviceManager.pushNewUNTS(deviceName, deviceID);
                        UNTSStateMachine unts = deviceManager.queryForUNTS(deviceName);
                        unts.setEvent(alertTypeID);
                        opThread = new Thread(unts);
                        break;
                    case 3:
                        System.out.println("pushing new state to WEMO: " + deviceName);
                        deviceManager.pushNewWEMO(deviceName, deviceID);
                        WEMOStateMachine wemo = deviceManager.queryForWEMO(deviceName);
                        wemo.setEvent(alertTypeID);
                        opThread = new Thread(wemo);
                        break;
                    case 4:
                        System.out.println("pushing new state to PHLE: " + deviceName);
                        deviceManager.pushNewPHLE(deviceName, deviceID);
                        PHLEStateMachine phle = deviceManager.queryForPHLE(deviceName);
                        phle.setEvent(alertTypeID);
                        opThread = new Thread(phle);
                        break;
                    default:
                        opThread = new Thread(new StateMachine("empty", 0));
                        break;
                }
                    opThread.start();
            }
        }
        catch (InterruptedException | ExecutionException e){
            System.out.println("Caught exception Interrupted or Execution");
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

}
