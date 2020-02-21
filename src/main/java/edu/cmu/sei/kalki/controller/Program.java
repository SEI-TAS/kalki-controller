package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.database.Postgres;
import edu.cmu.sei.kalki.db.utils.Config;
import edu.cmu.sei.kalki.db.utils.TestDB;

public class Program
{
    /**
     * Initializes the database and alert listeners.
     */
    public static void main(String[] args) {
        try
        {
            Config.load("config.json");

            if(args.length >= 2 && args[0].equals("test"))
            {
                String testFile = args[1];
                TestDB.setupTestDBFromConfig(testFile);
            }
            else
            {
                Postgres.initializeFromConfig();
            }

            MainController mainController = new MainController();
            mainController.initListeners();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
