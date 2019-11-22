package edu.cmu.sei.kalki.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

public class TestWEMOStateMachine extends TestIOTController {

    @Test
    public void testNormalStateReset() {
        int devId = insertData(3, 1, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testNormalWemoTimeOn() {
        int devId = insertData(3, 1, "wemo-time-on");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalDeviceUnavailable() {
        int devId = insertData(3, 1, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalWemoCurrentMwGreaterLow() {
        int devId = insertData(3, 1, "wemo-current-mw-greater-low");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalWemoTodayKwh() {
        int devId = insertData(3, 1, "wemo-today-kwh");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalWemoCurrentMwGreaterHigh() {
        int devId = insertData(3, 1, "wemo-current-mw-greater-high");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousDlcMotionSense() {
        int devId = insertData(1, 2, "dlc-motion-sense");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousStateReset() {
        int devId = insertData(3, 2, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testSuspiciousWemoCurrentMwGreaterLow() {
        int devId = insertData(3, 2, "wemo-current-mw-greater-low-suspicious");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousWemoTodayKwh() {
        int devId = insertData(3, 2, "wemo-today-kwh");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousDeviceUnavailable() {
        int devId = insertData(3, 2, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousWemoCurrentMwGreaterHigh() {
        int devId = insertData(3, 2, "wemo-current-mw-greater-high");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousWemoTimeOn() {
        int devId = insertData(3, 2, "wemo-time-on");
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
