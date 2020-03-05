package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.AlertDAO;
import edu.cmu.sei.kalki.db.daos.AlertTypeDAO;
import edu.cmu.sei.kalki.db.daos.DeviceDAO;
import edu.cmu.sei.kalki.db.models.Alert;
import edu.cmu.sei.kalki.db.models.AlertType;
import edu.cmu.sei.kalki.db.models.Device;

import edu.cmu.sei.kalki.db.database.Postgres;
import edu.cmu.sei.kalki.db.listeners.InsertHandler;
import edu.cmu.sei.kalki.db.listeners.InsertListener;

/**
 * This class handles the initialization of the Postgres database connection as well as the initialization of
 * the alert listeners and their handler
 */
public class MainController implements InsertHandler {

    private StateMachineManager stateMachineManager;

    public MainController(){
        this.stateMachineManager = new StateMachineManager();
    }

    /**
     * Listens for alerts added to database retrieves the device associated with the alert and its type
     * and generates a new state machine for this device pushing the alert type to the statemachine in a thread
     * @param id The id of the alert inserted into the database
     */
    @Override
    public void handleNewInsertion(int id) {
        try {
            Alert receivedAlert = AlertDAO.findAlert(id);
            if (receivedAlert == null) {
                System.out.println("Newly inserted alert not found");
                return;
            }

            AlertType alertType = AlertTypeDAO.findAlertType(receivedAlert.getAlertTypeId());
            System.out.println("Alert Type Found: " + alertType.getName());

            Device device = DeviceDAO.findDeviceByAlert(receivedAlert);

            Thread process = new Thread(() -> {
                    try {
                        StateMachine stateMachine = stateMachineManager.getStateMachine(device);
                        stateMachine.executeStateChange(alertType);
                    }
                    catch (Exception e) {
                        System.out.println("Error getting next state: " + e.toString());
                        e.printStackTrace();
                    }
                }
            );
            process.start();
        }
        catch (Exception e) {
            System.out.println("Error handling new alert insertion: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Initialize database connection.
     */
    public void initDB()
    {
        Postgres.initializeFromConfig();
    }

    /**
     * Initializes database listener for the insertion of new alerts
     */
    public void initListeners(){
        InsertListener.clearHandlers();
        InsertListener.addHandler(Postgres.TRIGGER_NOTIF_NEW_ALERT, this);
        InsertListener.startListening();
    }

    /**
     * Stops listening for alert insertions.
     */
    public void stopListeners() {
        InsertListener.stopListening();
    }
}
