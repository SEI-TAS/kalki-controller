package edu.cmu.sei.ttg.kalki.controller;

import static org.junit.Assert.assertEquals;

import edu.cmu.sei.ttg.kalki.controller.JavaDevices.DLCStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.PHLEStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.UNTSStateMachine;
import edu.cmu.sei.ttg.kalki.controller.JavaDevices.WEMOStateMachine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDeviceManager {

    DeviceManager mainManager;

    @BeforeEach
    public void testManagerInit(){
        mainManager = new DeviceManager();
    }

    @Test
    public void testPHLEDeviceSearch(){
        mainManager.pushNewPHLE(new PHLEStateMachine("phle00", 0, 1));
        mainManager.pushNewPHLE(new PHLEStateMachine("phle01", 1, 1));
        assertEquals("phle00", mainManager.queryForPHLE("phle00", 0, 1).getName());
        assertEquals("phle01", mainManager.queryForPHLE("phle01", 1, 1).getName());
    }
    @Test
    public void testUNTSDeviceSearch(){
        mainManager.pushNewUNTS(new UNTSStateMachine("unts00", 0, 1));
        mainManager.pushNewUNTS(new UNTSStateMachine("unts01", 1, 1));
        assertEquals("unts00", mainManager.queryForUNTS("unts00", 0, 1).getName());
        assertEquals("unts01", mainManager.queryForUNTS("unts01", 1, 1).getName());
    }
    @Test
    public void testDLCDeviceSearch(){
        mainManager.pushNewDLC(new DLCStateMachine("dlc00", 0, 1));
        mainManager.pushNewDLC(new DLCStateMachine("dlc01", 1, 1));
        assertEquals("dlc00", mainManager.queryForDLC("dlc00", 0, 1).getName());
        assertEquals("dlc01", mainManager.queryForDLC("dlc01", 1, 1).getName());
    }
    @Test
    public void testWEMODeviceSearch(){
        mainManager.pushNewWEMO(new WEMOStateMachine("wemo00", 0, 1));
        mainManager.pushNewWEMO(new WEMOStateMachine("wemo01", 1, 1));
        assertEquals("wemo00", mainManager.queryForWEMO("wemo00", 0, 1).getName());
        assertEquals("wemo01", mainManager.queryForWEMO("wemo01", 1, 1).getName());
    }
    @Test
    public void testPHLEEventSet(){ ;
        mainManager.pushNewPHLE(new PHLEStateMachine("e", 2, 1));
        PHLEStateMachine device = mainManager.queryForPHLE("e", 2, 1);
        device.setEvent("brute-force");
        assertEquals(device.getEvent(), "brute-force");
    }
    @Test
    public void testDLCEventSet(){ ;
        mainManager.pushNewDLC(new DLCStateMachine("e", 2, 1));
        DLCStateMachine device = mainManager.queryForDLC("e", 2, 1);
        device.setEvent("brute-force");
        assertEquals(device.getEvent(), "brute-force");
    }

    @Test
    public void testUNTSEventSet(){ ;
        mainManager.pushNewUNTS(new UNTSStateMachine("e", 2, 1));
        UNTSStateMachine device = mainManager.queryForUNTS("e", 2, 1);
        device.setEvent("brute-force");
        assertEquals(device.getEvent(), "brute-force");
    }

    @Test
    public void testWEMOEventSet(){ ;
        mainManager.pushNewWEMO(new WEMOStateMachine("e", 2, 1));
        WEMOStateMachine device = mainManager.queryForWEMO("e", 2, 1);
        device.setEvent("brute-force");
        assertEquals(device.getEvent(), "brute-force");
    }
}
