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
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * This class handles the initialization of the Postgres database connection as well as the initialization of
 * the alert listeners and their handler
 */

public class IOTController implements InsertHandler{

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
        //System.out.println("Here in handler");
        Alert receivedAlert = Postgres.findAlert(id);
        if (receivedAlert == null) {
            System.out.println("alert not found");
        } else {
            System.out.println("alert found");
            Device foundDevice = Postgres.findDeviceByAlert(receivedAlert);
            String deviceName = foundDevice.getName();
            int deviceID = foundDevice.getId();
            int deviceTypeID = foundDevice.getType().getId();
            int alertTypeID = receivedAlert.getAlertTypeId();
            String eventName = Postgres.findAlertType(alertTypeID).getName();
            //System.out.println("Alert Type Name: " + alertTypeName);
            System.out.println(deviceID);
            Thread handlerThread = new Thread()
            {
                @Override
                public void run() {
                    try {
                        switch (deviceTypeID){
                            case 1:
                                deviceManager.pushNewDLC(deviceName, deviceID);
                                DLCStateMachine foundDevice = deviceManager.queryForDLC(deviceName, deviceID);
                                synchronized (foundDevice){
                                    foundDevice.setEvent(eventName);
                                    foundDevice.callNative();
                                }
                            case 2:

                            case 3:

                            case 4:

                            default:
                        }
                    }
                    catch (UnsatisfiedLinkError e){

                    }
                }
            };
            handlerThread.start();
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
