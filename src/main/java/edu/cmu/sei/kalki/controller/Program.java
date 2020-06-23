package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.utils.Config;
import edu.cmu.sei.kalki.db.utils.LoggerSetup;

public class Program
{
    /**
     * Initializes the database and alert listeners.
     */
    public static void main(String[] args) {
        try
        {
            Config.load("config.json");
            LoggerSetup.setup();

            MainController mainController = new MainController();
            mainController.initDB();
            mainController.initListeners();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
