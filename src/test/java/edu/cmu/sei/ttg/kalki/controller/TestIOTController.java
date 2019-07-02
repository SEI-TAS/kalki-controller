package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.Alert;
import edu.cmu.sei.ttg.kalki.models.Device;
import edu.cmu.sei.ttg.kalki.models.DeviceStatus;
import edu.cmu.sei.ttg.kalki.models.Group;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class TestIOTController {

    private IOTController mainIOTController;

    @Before
    public void testIOTInit(){
        mainIOTController = new IOTController();
        mainIOTController.initializeDatabase();
        mainIOTController.initListeners(mainIOTController);
    }

    @Test
    public void testIOTMain(){
        mainIOTController = new IOTController();
        mainIOTController.initializeDatabase();
        mainIOTController.initListeners(mainIOTController);
        Device testDevice;
        DeviceStatus testStatus;
        Postgres.insertGroup(new Group(10, "TestDevices"));
        for(int i = 1; i < 5; i++){
            testDevice = new Device("test_device", "its a "+i,
                    i, 10, "0.0.0.0", 5, 5);
            testDevice.insert();
            wait(2);
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            wait(2);
            for(int j = 1; j < 6; j++){
                Alert testAlert = new Alert("new-alert", testStatus.getId(), j);
                testAlert.insert();
                wait(2);
            }
        }
        System.exit(23);
    }

    static void wait(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (InterruptedException e){
            System.out.println("Error in wait");
        }
    }
}
