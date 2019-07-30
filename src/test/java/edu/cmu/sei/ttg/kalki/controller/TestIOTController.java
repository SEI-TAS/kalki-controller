package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.*;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

public class TestIOTController{

    private IOTController mainIOTController;
    private static boolean hasRun;
    @BeforeClass
    public static void createTestDB() {
        if (!hasRun) {
            String rootPassword = "kalkipass";  //based on run script
            String dbHost = "localhost";        //based on run script
            String dbPort = "5432";             //based on run script
            String dbName = "kalkidb_test";
            String dbUser = "kalkiuser_test";
            String dbPass = "kalkipass";

            try {
                // Recreate DB and user.
                Postgres.removeDatabase(rootPassword, dbName);
                Postgres.removeUser(rootPassword, dbUser);
                Postgres.createUserIfNotExists(rootPassword, dbUser, dbPass);
                Postgres.createDBIfNotExists(rootPassword, dbName, dbUser);

                //initialize test DB
                Postgres.initialize(dbHost, dbPort, dbName, dbUser, dbPass);
                Postgres.resetDatabase();
            } catch (Exception e) {
                System.out.println(e);
            }
            hasRun = true;
        }
        IOTController testController= new IOTController();
        testController.initListeners(testController);

    }

    @Test
    public void testPHLEAlerts(){
        Postgres.insertGroup(new Group(14, "TestPHLE"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        System.out.println("Testing alerts for PHLE");
        int[] alerts = {1, 2, 3, 4, 6, 16, 17};
        for (int i:alerts) {
            testDevice = new Device("test_device", "its a PHLE",
                    4, 14, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), 5);
            testAlert.insert();
            for (int j = 0; j < 2; j++) {
                testAlert = new Alert("new-alert", testStatus.getId(), i);
                testAlert.insert();
                wait(2);
            }
        }
    }

    @Test
    public void testDLCAlerts(){
        Postgres.insertGroup(new Group(14, "TestPHLE"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        System.out.println("Testing alerts for DLC");
        int[] alerts = {1, 2, 3, 4, 6, 15};
        for (int i:alerts) {
            testDevice = new Device("test_device", "its a DLC",
                    1, 14, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), 5);
            testAlert.insert();
            for (int j = 0; j < 2; j++) {
                testAlert = new Alert("new-alert", testStatus.getId(), i);
                testAlert.insert();
                wait(2);
            }
        }
    }

    @Test
    public void testWEMOAlerts(){
        Postgres.insertGroup(new Group(14, "TestPHLE"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        System.out.println("Testing alerts for WEMO");
        int[] alerts = {1, 2, 3, 4, 6, 18, 19, 20, 21, 22, 23};
        for (int i:alerts) {
            testDevice = new Device("test_device", "its a WEMO",
                    3, 14, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), 5);
            testAlert.insert();
            for (int j = 0; j < 2; j++) {
                testAlert = new Alert("new-alert", testStatus.getId(), i);
                testAlert.insert();
                wait(2);
           }
        }
    }
    
    @Test
    public void testUNTSAlerts(){
        Postgres.insertGroup(new Group(14, "TestPHLE"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        System.out.println("Testing alerts for UNTS");
        int[] alerts = {1, 2, 3, 4, 6, 16, 17};
        for (int i:alerts) {
            testDevice = new Device("test_device", "its a UNTS",
                    2, 14, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), 5);
            testAlert.insert();
            for (int j = 0; j < 2; j++) {
                testAlert = new Alert("new-alert", testStatus.getId(), i);
                testAlert.insert();
                wait(2);
            }
        }
    }

    @Test
    public void testSecurityStateInsertion(){
        Device testDevice;
        DeviceStatus testStatus;
        Alert testalert;
        testDevice = new Device("test_device", "its a database tester",
                1, 13, "0.0.0.0", 5, 5);
        testDevice.insert();
        wait(2);
        DeviceSecurityState testState = new DeviceSecurityState(testDevice.getId(), 1);
        testState.insert();
        wait(2);
        testDevice.setCurrentState(testState);
        testDevice.insertOrUpdate();
        wait(2);
        System.out.println(Postgres.findDevice(testDevice.getId()));
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
