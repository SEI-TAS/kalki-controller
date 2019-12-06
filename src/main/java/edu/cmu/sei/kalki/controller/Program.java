package edu.cmu.sei.kalki.controller;

public class Program
{
    /**
     * Initializes the database with the given parameters from the databaseVars JSON file, resets the database, and
     * initialized the alert listeners
     */
    public static void main(String[] args) {
        System.out.println(System.getProperty( "java.library.path" ));
        MainController mainController = new MainController();
        mainController.initializeDatabase();
        mainController.initListeners(mainController);
    }
}