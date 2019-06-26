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

import static org.junit.Assert.*;

public class IOTControllerTest {

    IOTController testController;

    DeviceManager testManager;

    @BeforeClass
    public void setUp(){
        this.testController = new IOTController();
        this.testController.initializeDatabase();
        this.testController.initListeners(testController);
        System.out.println("Finished setting up manager");
        //Setup Groups and Models required
        Postgres.insertGroup(new Group(11, "TestDLCDeviceAdd"));
        wait(2);
        Postgres.insertGroup(new Group(12, "TestUNTSDeviceAdd"));
        wait(2);
        Postgres.insertGroup(new Group(12, "TestWEMODeviceAdd"));
        wait(2);
        Postgres.insertGroup(new Group(14, "TestPHLEDeviceAdd"));
        wait(2);
    }

    @Test
    public void testDLCDeviceHandling(){
        System.out.println("testDLC Device manager");
        Device test_dlc0 = new Device("DLC00", "its a dlc",
                1, 11, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewDLC(test_dlc0.getName(), test_dlc0.getId());
        test_dlc0.insert();
        wait(3);
        test_dlc0 = new Device("DLC01", "its a dlc",
                1, 11, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewDLC(test_dlc0.getName(), test_dlc0.getId());
        test_dlc0.insert();
        wait(3);
        test_dlc0 = new Device("DLC02", "its a dlc",
                1, 11, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewDLC(test_dlc0.getName(), test_dlc0.getId());
        test_dlc0.insert();
        wait(3);
        System.out.println("setup up devices");

    }

    public void testPHLEDeviceHandling(){
        Device test_phle = new Device("PHLE00", "its a phle",
                4, 14, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewPHLE(test_phle.getName(), test_phle.getId());
        test_phle.insert();
        wait(3);
        test_phle = new Device("PHLE01", "its a phle",
                4, 14, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewPHLE(test_phle.getName(), test_phle.getId());
        test_phle.insert();
        wait(3);
        test_phle = new Device("PHLE02", "its a phle",
                4, 14, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewPHLE(test_phle.getName(), test_phle.getId());
        test_phle.insert();
        wait(3);

    }

    public void testUNTSDeviceHandling(){
        Device test_unts = new Device("UNTS00", "its a unts",
                2, 12, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewUNTS(test_unts.getName(),test_unts.getId());
        test_unts.insert();
        wait(3);
        test_unts = new Device("UNTS01", "its a unts",
                2, 12, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewUNTS(test_unts.getName(),test_unts.getId());
        test_unts.insert();
        wait(3);
        test_unts = new Device("UNTS02", "its a unts",
                2, 12, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewUNTS(test_unts.getName(),test_unts.getId());
        test_unts.insert();
        wait(3);

    }

    public void testWEMODeviceHandling(){
        Device test_WEMO = new Device("WEMO00", "its a wemo",
                3, 13, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewWEMO(test_WEMO.getName(), test_WEMO.getId());
        test_WEMO.insert();
        wait(3);
        test_WEMO = new Device("WEMO01", "its a wemo",
                3, 13, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewWEMO(test_WEMO.getName(), test_WEMO.getId());
        test_WEMO.insert();
        wait(3);
        test_WEMO = new Device("WEMO02", "its a wemo",
                3, 13, "0.0.0.0", 5, 5);
        testController.deviceManager.pushNewWEMO(test_WEMO.getName(), test_WEMO.getId());
        test_WEMO.insert();
        wait(3);

    }

    public void testDLCAlertHandling(){
        Device dlcDevice = new Device("DLCAlertTester", "its a dlc for testing alerts",
                1, 11, "0.0.0.0", 5, 5);
        //
        dlcDevice.insert();
        wait(2);
        DeviceStatus dlcStatus = new DeviceStatus(dlcDevice.getId());
        dlcStatus.insert();
        wait(2);
        Alert dlcTestAlert = new Alert("dlc-brute-force", dlcStatus.getId(), 1);
        dlcTestAlert.insert();
        wait(2);
        //check
        //endcheck
        dlcTestAlert = new Alert("dlc-default-login", dlcStatus.getId(), 2);
        dlcTestAlert.insert();
        wait(2);
        //check
        //endcheck
        dlcTestAlert = new Alert("dlc-device-unavailable", dlcStatus.getId(), 3);
        dlcTestAlert.insert();
        wait(2);
        //check
        //endcheck
        dlcTestAlert = new Alert("dlc-max-login-attempts", dlcStatus.getId(), 4);
        dlcTestAlert.insert();
        wait(2);
        //check

        //endcheck
        dlcTestAlert = new Alert("dlc-state-reset", dlcStatus.getId(), 5);
        dlcTestAlert.insert();
        wait(2);
        //check
    }

    public void testUNTSAlertHandling(){

        Device untsDevice = new Device("UNTSAlertTester", "its a unts for testing alerts",
                1, 14, "0.0.0.0", 5, 5);
        //
        untsDevice.insert();
        wait(2);
        DeviceStatus untsStatus = new DeviceStatus(untsDevice.getId());
        untsStatus.insert();
        wait(2);
        Alert untsTestAlert = new Alert("unts-brute-force", untsStatus.getId(), 1);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
        untsTestAlert = new Alert("unts-default-login", untsStatus.getId(), 2);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
        untsTestAlert = new Alert("unts-device-unavailable", untsStatus.getId(), 3);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
        untsTestAlert = new Alert("unts-max-login-attempts", untsStatus.getId(), 4);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
        untsTestAlert = new Alert("unts-state-reset", untsStatus.getId(), 5);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
        //acceleration
        untsTestAlert = new Alert("unts-acceleration", untsStatus.getId(), 6);
        untsTestAlert.insert();
        wait(2);
        //gyro
        untsTestAlert = new Alert("unts-gyro", untsStatus.getId(), 7);
        untsTestAlert.insert();
        wait(2);
        //gyro-secondary
        untsTestAlert = new Alert("unts-gyro-secondary", untsStatus.getId(), 8);
        untsTestAlert.insert();
        wait(2);
        //unts-magnetometer
        untsTestAlert = new Alert("unts-reset", untsStatus.getId(), 5);
        untsTestAlert.insert();
        wait(2);
        //unts-temperature
        untsTestAlert = new Alert("unts-temperature", untsStatus.getId(), 12);
        untsTestAlert.insert();
        wait(2);
        //unts alert not for device
        untsTestAlert = new Alert("Alert-not-inUNTS", untsStatus.getId(), 18);
        untsTestAlert.insert();
        wait(2);
        //check
        //endcheck
    }

    public void testWEMOAlertHandling(){
        Device wemoDevice = new Device("DLCAlertTester", "its a wemo for testing alerts",
                1, 11, "0.0.0.0", 5, 5);
        //
        wemoDevice.insert();
        wait(2);
        DeviceStatus wemoStatus = new DeviceStatus(wemoDevice.getId());
        wemoStatus.insert();
        wait(2);
        Alert wemoTestAlert = new Alert("wemo-brute-force", wemoStatus.getId(), 1);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-default-login", wemoStatus.getId(), 2);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-device-unavailable", wemoStatus.getId(), 3);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-max-login-attempts", wemoStatus.getId(), 4);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-state-reset", wemoStatus.getId(), 5);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-current-mw-greater-low", wemoStatus.getId(), 18);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-current-mw-greater-high", wemoStatus.getId(), 19);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-state-reset", wemoStatus.getId(), 5);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-current-mw-same-group", wemoStatus.getId(), 20);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-last-change", wemoStatus.getId(), 21);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-time-on", wemoStatus.getId(), 22);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
        wemoTestAlert = new Alert("wemo-today-kwh", wemoStatus.getId(), 23);
        wemoTestAlert.insert();
        wait(2);
        //check
        //endcheck
    }

    public void testPHLEAlertHandling(){
        Device phleDevice = new Device("PHLEAlertTester", "its a phle for testing alerts",
                4, 14, "0.0.0.0", 5, 5);
        phleDevice.insert();
        wait(2);
        DeviceStatus phleStatus = new DeviceStatus(phleDevice.getId());
        phleStatus.insert();
        wait(2);
        //phle alert 1
        Alert phleTestAlert = new Alert("phle-brute-force", phleStatus.getId(), 1);
        phleTestAlert.insert();
        wait(2);
        //phle alert 2
        phleTestAlert = new Alert("phle-default-login", phleStatus.getId(), 2);
        phleTestAlert.insert();
        wait(2);
        //phle alert 3
        phleTestAlert = new Alert("phle-device-unavailable", phleStatus.getId(), 3);
        phleTestAlert.insert();
        wait(2);
        //phle alert reset
        phleTestAlert = new Alert("phle-state-reset", phleStatus.getId(), 5);
        phleTestAlert.insert();
        wait(2);
        //phle alert 4
        phleTestAlert = new Alert("phle-max-login-attempts", phleStatus.getId(), 4);
        phleTestAlert.insert();
        wait(2);
        //phle alert 16
        phleTestAlert = new Alert("phle-time-ff", phleStatus.getId(), 16);
        phleTestAlert.insert();
        wait(2);
        //phle alert 17
        phleTestAlert = new Alert("phle-odd-one-out", phleStatus.getId(), 17);
        phleTestAlert.insert();
        wait(2);
    }

    private static void wait(int time){
        try {
            TimeUnit.SECONDS.sleep(time);
        }
        catch (InterruptedException e){
            System.out.println("Interrupted Exception in Wait");
        }
    }

}