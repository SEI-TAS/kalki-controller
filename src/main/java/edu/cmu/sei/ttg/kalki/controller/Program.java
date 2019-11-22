package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.controller.MainController;

public class Program
{
    /**
     * Initializes the database with the given parameters from the databaseVars JSON file, resets the database, and
     * initialized the alert listeners
     */
    public static void main(String[] args) {
        MainController mainController = new MainController();
        mainController.initializeDatabase();
        mainController.initListeners(mainController);

        mainController.runTest();

    /*
        UNTSStateMachine fsm = new UNTSStateMachine("device00", 0);
        fsm.setEvent("brute-force");
        new Thread(fsm).start();
     */

    }
}
