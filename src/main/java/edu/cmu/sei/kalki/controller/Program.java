package edu.cmu.sei.kalki.controller;

public class Program
{
    // TODO: Automatically generate <>STateMachine java files from template.
    // TODO: Automatically generate C files from template.
    // TODO: Ensure tests work.
    // TODO: Ensure running individual tests thrhough IntelliJ Idea work.
    // TODO: Group FSM c code in one subfolder.

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
