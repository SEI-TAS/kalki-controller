package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.controller.JavaDevices.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the initialization of the Postgres database connection as well as the initialization of
 * the alert listeners and their handler
 */

public class IOTController implements InsertHandler {

    DeviceManager deviceManager = new DeviceManager();

    /**
     * Initializes the database with the given parameters from the databaseVars JSON file, resets the database, and
     * initialized the alert listeners
     */
    public static void main(String[] args) {
        IOTController mainController = new IOTController();
        mainController.initializeDatabase();
        mainController.initListeners(mainController);
    }

    /**
     *
     * @param id The id of the alert inserted into the database
     * Listens for alerts added to database retrieves the device associated with the alert and its type
     * and generates a new state machine for this device pushing the alert type to the statemachine native code in a thread
     *
     */
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
                    DLCStateMachine dlc = deviceManager.queryForDLC(deviceName, deviceID);
                    dlc.setEvent(alertTypeName);
                    opThread = new Thread(dlc);
                    break;
                case 2:
                    System.out.println("pushing new state to UNTS: " + deviceName);
                    deviceManager.pushNewUNTS(deviceName, deviceID);
                    UNTSStateMachine unts = deviceManager.queryForUNTS(deviceName, deviceID);
                    unts.setEvent(alertTypeName);
                    opThread = new Thread(unts);
                    break;
                case 3:
                    System.out.println("pushing new state to WEMO: " + deviceName);
                    deviceManager.pushNewWEMO(deviceName, deviceID);
                    WEMOStateMachine wemo = deviceManager.queryForWEMO(deviceName, deviceID);
                    wemo.setEvent(alertTypeName);
                    opThread = new Thread(wemo);
                    break;
                case 4:
                    System.out.println("pushing new state to PHLE: " + deviceName);
                    deviceManager.pushNewPHLE(deviceName, deviceID);
                    PHLEStateMachine phle = deviceManager.queryForPHLE(deviceName, deviceID);
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

    /**
     * Pulls relevant data from the databaseVars JSON file and call initialize using these values
     * Resets database back to default state
     */
    void initializeDatabase(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("databaseVars.json"));
            JSONObject json = (JSONObject) obj;
            String ip = (String) json.get("ip");
            String port = (String) json.get("port");
            String dbName = (String) json.get("dbName");
            String dbUser = (String) json.get("dbUser");
            String dbPassword = (String) json.get("dbPassword");
            //System.out.println("ip: "+ ip + "port: " + port + "dbName: " + dbName + "dbUser: " + dbUser + "dbPassword: " + dbPassword);
            Postgres.initialize(ip, port, dbName, dbUser, dbPassword);
            Postgres.resetDatabase();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public IOTController(){

    }

    /**
     *
     * @param alertHandler requires object that inherits from alertHandler
     * Initializes database listener for the insertion of new alerts
     */
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
