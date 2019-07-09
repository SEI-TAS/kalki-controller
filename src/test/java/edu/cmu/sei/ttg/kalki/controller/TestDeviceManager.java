package edu.cmu.sei.ttg.kalki.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class TestDeviceManager {

    DeviceManager mainManager;

    @Before
    public void testManagerInit(){
        mainManager = new DeviceManager();
    }
    @Test
    public void testPHLEDeviceSearch(){
        mainManager.pushNewPHLE("phle00", 0);
        mainManager.pushNewPHLE("phle01", 1);
        assertEquals("phle00", mainManager.queryForPHLE("phle00", 0).getName());
        assertEquals("phle01", mainManager.queryForPHLE("phle01", 1).getName());
        assertEquals("empty", mainManager.queryForPHLE("phle02", 1).getName());
    }
    @Test
    public void testUNTSDeviceSearch(){
        mainManager.pushNewUNTS("unts00", 0);
        mainManager.pushNewUNTS("unts01", 1);
        assertEquals("unts00", mainManager.queryForUNTS("unts00", 0).getName());
        assertEquals("unts01", mainManager.queryForUNTS("unts01", 1).getName());
        assertEquals("empty", mainManager.queryForUNTS("unts02", 1).getName());
    }
    @Test
    public void testDLCDeviceSearch(){
        mainManager.pushNewDLC("dlc00", 0);
        mainManager.pushNewDLC("dlc01", 1);
        assertEquals("dlc00", mainManager.queryForDLC("dlc00", 0).getName());
        assertEquals("dlc01", mainManager.queryForDLC("dlc01", 1).getName());
        assertEquals("empty", mainManager.queryForDLC("dlc02", 1).getName());
    }
    @Test
    public void testWEMODeviceSearch(){
        mainManager.pushNewWEMO("wemo00", 0);
        mainManager.pushNewWEMO("wemo01", 1);
        assertEquals("wemo00", mainManager.queryForWEMO("wemo00", 0).getName());
        assertEquals("wemo01", mainManager.queryForWEMO("wemo01", 1).getName());
        assertEquals("empty", mainManager.queryForWEMO("wemo02", 1).getName());
    }


}
