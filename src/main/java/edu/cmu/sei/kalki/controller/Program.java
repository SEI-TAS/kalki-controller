package edu.cmu.sei.kalki.controller;

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
    }
}
