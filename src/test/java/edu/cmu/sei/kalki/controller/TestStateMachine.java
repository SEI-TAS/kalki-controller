package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.DeviceDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.cmu.sei.kalki.db.models.*;

public class TestStateMachine extends TestBase
{

    @Test
    public void testNormalMaxLoginAttempts() {
        int devId = insertData(1, "max-login-attempts");
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Device d = DeviceDAO.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

}
