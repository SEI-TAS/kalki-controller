package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.AlertDAO;
import edu.cmu.sei.kalki.db.daos.DeviceDAO;
import edu.cmu.sei.kalki.db.models.Alert;
import edu.cmu.sei.kalki.db.models.Device;

import edu.cmu.sei.kalki.db.database.Postgres;
import edu.cmu.sei.kalki.db.listeners.InsertHandler;
import edu.cmu.sei.kalki.db.listeners.InsertListener;

import java.util.logging.Logger;

/**
 * This class handles the initialization of the Postgres database connection as well as the initialization of
 * the alert listeners and their handler
 */
public class MainController implements InsertHandler {
    protected static final Logger logger = Logger.getLogger(MainController.class.getName());

    private StateMachineManager stateMachineManager;

    public MainController(){
        this.stateMachineManager = new StateMachineManager();
    }

    /**
     * Listens for alerts added to database retrieves the device associated with the alert and its type
     * and generates a new state machine for this device pushing the alert type to the statemachine in a thread
     * @param newAlertId The id of the alert inserted into the database
     */
    @Override
    public void handleNewInsertion(int newAlertId) {
        try {
            Alert receivedAlert = AlertDAO.findAlert(newAlertId);
            if (receivedAlert == null) {
                logger.info("Newly inserted alert not found");
                return;
            }

            Device device = DeviceDAO.findDeviceByAlert(receivedAlert);
            StateMachine stateMachine = stateMachineManager.getStateMachine(device);
            stateMachine.executeStateChangeIfNeeded(receivedAlert);
        }
        catch (Exception e) {
            logger.warning("Error handling new alert insertion: " + e.toString());
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
