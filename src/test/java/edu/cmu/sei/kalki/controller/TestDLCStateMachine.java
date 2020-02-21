package edu.cmu.sei.kalki.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.database.Postgres;

public class TestDLCStateMachine extends TestBase
{

    @Test
    public void testNormalMaxLoginAttempts() {
        int devId = insertData(1, 1, "max-login-attempts");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalStateReset() {
        int devId = insertData(1, 1, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
     }

    @Test
    public void testNormalDeviceUnavailable() {
        int devId = insertData(1, 1, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalDlcMotionSense() {
        int devId = insertData(1, 1, "dlc-motion-sense");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousDlcMotionSense() {
        int devId = insertData(1, 2, "dlc-motion-sense");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousStateReset() {
        int devId = insertData(1, 2, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testSuspiciousMaxLoginAttempts() {
        int devId = insertData(1, 2, "max-login-attempts");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testAttackStateReset() {
        int devId = insertData(1, 3, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }
}
