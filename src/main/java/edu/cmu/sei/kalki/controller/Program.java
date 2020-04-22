package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.utils.Config;

public class Program
{
    /**
     * Initializes the database and alert listeners.
     */
    public static void main(String[] args) {
        try
        {
            Config.load("config.json");

            MainController mainController = new MainController();
            mainController.initDB();
            mainController.initListeners();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
