package kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.listeners.InsertHandler;
import edu.cmu.sei.ttg.kalki.listeners.InsertListener;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceType;

import java.util.ArrayList;

public class IOTController implements InsertHandler {

    private DeviceManager deviceManager;

    public static void main(String[] args) {
        IOTController main = new IOTController();
        main.initListeners();
        System.out.println("Hi we finished everything");
        //Postgres.insertDevice(new Device("name", "description", 10, 5, "0.0.0.0", 10, 10));
    }
    @Override
    public void handleNewInsertion(int i) {

    }
    private static void initializeDatabase(){
        Postgres.initialize("0.0.0.0", "5432", "kalkidb" , "kalkiuser", "kalkipass");
    }
    private IOTController(){
        initializeDatabase();
        deviceManager = new DeviceManager();
    }
    private void initListeners(){
        InsertListener.startUpListener("deviceinsert", this);
        InsertListener.startUpListener("alerthistoryinsert", this);
    }
}
