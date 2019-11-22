package edu.cmu.sei.kalki.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

import edu.cmu.sei.kalki.controller.fsm.StateMachine;
import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.*;

/**
 * This class handles the initialization of the Postgres database connection as well as the initialization of
 * the alert listeners and their handler
 */

public class MainController implements InsertHandler{

    private StateMachineManager stateMachineManager;

    public MainController(){
        this.stateMachineManager = new StateMachineManager();
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
            int currentState = foundDevice.getCurrentState().getStateId();
            int deviceTypeID = foundDevice.getType().getId();
            int alertTypeID = receivedAlert.getAlertTypeId();
            int samplingRate = foundDevice.getSamplingRate();
            String eventName = Postgres.findAlertType(alertTypeID).getName();

            System.out.println("Alert Type Name: " + eventName);
            try {
                Thread process = new Thread(() ->
                    {
                        StateMachine stateMachine = stateMachineManager.getStateMachine(deviceName, deviceID, currentState, deviceTypeID);
                        if(stateMachine != null)
                        {
                            stateMachine.setEvent(eventName);
                            stateMachine.callNative(foundDevice.getSamplingRate(), foundDevice.getDefaultSamplingRate());
                        }
                        else
                        {
                            System.out.println("State machine for given device type not found");
                        }
                    }
                );
                process.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Pulls relevant data from the databaseVars JSON file and call initialize using these values
     * Resets database back to default state
     */
    public void initializeDatabase(){
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param alertHandler requires object that inherits from alertHandler
     * Initializes database listener for the insertion of new alerts
     */
    public void initListeners(InsertHandler alertHandler){
        InsertListener.addHandler("alerthistoryinsert", alertHandler);
        InsertListener.startListening();
        try {
            TimeUnit.SECONDS.sleep(5);
        }
        catch(InterruptedException e){
            System.out.println("caught exception in init listeners");
        }

    }
}
