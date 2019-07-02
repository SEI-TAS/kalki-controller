package edu.cmu.sei.ttg.kalki.controller;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
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
    public void testPHLEAlerts(){
        Postgres.insertGroup(new Group(14, "TestPHLE"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        testDevice = new Device("test_device", "its a PHLE",
                4, 14, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing basic alerts PHLE");
        for (int i = 1; i < 6; i = i+2) {
            testDevice = new Device("test_device", "its a PHLE",
                    4, 14, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), i);
            testAlert.insert();
            wait(2);
            testAlert = new Alert("new-alert", testStatus.getId(), i+1);
            testAlert.insert();
        }
        testDevice = new Device("test_device", "its a PHLE",
                4, 14, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing unique PHLE Alerts");
        wait(2);
        int[] uniqueAlertQueue = {16, 17};
        for (int alertType: uniqueAlertQueue) {
            testAlert = new Alert("new-alert", testStatus.getId(), alertType);
            System.out.println("Alert Type: " + Postgres.findAlertType(alertType).getName());
            testAlert.insert();
            wait(2);
        }
    }

    @Test
    public void testDLCAlerts(){
        Postgres.insertGroup(new Group(11, "TestDLC"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        testDevice = new Device("test_device", "its a DLC",
                1, 12, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing basic alerts DLC");
        for (int i = 1; i < 6; i = i+2) {
            testDevice = new Device("test_device", "its a DLC",
                    1, 12, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), i);
            testAlert.insert();
            wait(2);
            testAlert = new Alert("new-alert", testStatus.getId(), i+1);
            testAlert.insert();
        }
        testDevice = new Device("test_device", "its a DLC",
                1, 12, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing unique DLC Alerts");
        wait(2);
        int[] uniqueAlertQueue = {15};
        for (int alertType: uniqueAlertQueue) {
            testAlert = new Alert("new-alert", testStatus.getId(), alertType);
            System.out.println("Alert Type: " + Postgres.findAlertType(alertType).getName());
            testAlert.insert();
            wait(2);
        }
    }

    @Test
    public void testWEMOAlerts(){
        Postgres.insertGroup(new Group(13, "TestWEMO"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        testDevice = new Device("test_device", "its a WEMO",
                3, 13, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing basic alerts WEMO");
        for (int i = 1; i < 6; i = i+2) {
            testDevice = new Device("test_device", "its a WEMO",
                    3, 13, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), i);
            testAlert.insert();
            wait(2);
            testAlert = new Alert("new-alert", testStatus.getId(), i+1);
            testAlert.insert();
        }
        testDevice = new Device("test_device", "its a WEMO",
                3, 13, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing unique WEMO Alerts");
        wait(2);
        int[] uniqueAlertQueue = {18, 19, 20, 5, 21, 22, 23};
        for (int alertType: uniqueAlertQueue) {
            testAlert = new Alert("new-alert", testStatus.getId(), alertType);
            System.out.println("Alert Type: " + Postgres.findAlertType(alertType).getName());
            testAlert.insert();
            wait(2);
        }
    }
    
    @Test
    public void testUNTSAlerts(){
        Postgres.insertGroup(new Group(12, "TestUNTS"));
        Device testDevice;
        Alert testAlert;
        DeviceStatus testStatus;
        System.out.println("Testing basic alerts UNTS");
        for (int i = 1; i < 6; i = i+2) {
            testDevice = new Device("test_device", "its a UNTS",
                    2, 12, "0.0.0.0", 5, 5);
            testDevice.insert();
            testStatus = new DeviceStatus(testDevice.getId());
            testStatus.insert();
            testAlert = new Alert("new-alert", testStatus.getId(), i);
            testAlert.insert();
            wait(2);
            testAlert = new Alert("new-alert", testStatus.getId(), i+1);
            testAlert.insert();
        }
        testDevice = new Device("test_device", "its a UNTS",
                2, 12, "0.0.0.0", 5, 5);
        testDevice.insert();
        testStatus = new DeviceStatus(testDevice.getId());
        testStatus.insert();
        System.out.println("Testing unique UNTS Alerts");
        wait(2);
        int[] uniqueAlertQueue = {7, 8, 9, 5, 10, 11, 12, 5, 13, 14};
        for (int alertType: uniqueAlertQueue) {
            testAlert = new Alert("new-alert", testStatus.getId(), alertType);
            System.out.println("Alert Type: " + Postgres.findAlertType(alertType).getName());
            testAlert.insert();
            wait(2);
        }
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
