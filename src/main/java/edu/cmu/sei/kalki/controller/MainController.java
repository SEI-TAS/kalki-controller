package edu.cmu.sei.kalki.controller;

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
            Alert receivedAlert = Postgres.findAlert(id);
            if (receivedAlert == null) {
                System.out.println("Newly inserted alert not found");
                return;
            }

            AlertType alertType = Postgres.findAlertType(receivedAlert.getAlertTypeId());
            System.out.println("Alert Type Found: " + alertType.getName());

            Device device = Postgres.findDeviceByAlert(receivedAlert);

            Thread process = new Thread(() -> {
                    try {
                        StateMachine stateMachine = stateMachineManager.getStateMachine(device);
                        if (stateMachine != null) {
                            stateMachine.executeStateChange(alertType);
                        } else {
                            System.out.println("State machine for given device type not found");
                        }
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
     * Initializes database listener for the insertion of new alerts
     */
    public void initListeners(){
        InsertListener.clearHandlers();
        InsertListener.addHandler(Postgres.TRIGGER_NOTIF_NEW_ALERT, this);
        InsertListener.startListening();
    }
}
