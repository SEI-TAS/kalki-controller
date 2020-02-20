package edu.cmu.sei.kalki.controller;

public class Program
{
    /**
     * Initializes the database and alert listeners.
     */
    public static void main(String[] args) {
        try
        {
            MainController mainController = new MainController();
            mainController.initializeDatabase();
            mainController.initListeners();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
