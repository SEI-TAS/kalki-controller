package edu.cmu.sei.kalki.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

public class TestPHLEStateMachine extends TestIOTController {

    @Test
    public void testNormalStateReset() {
        int devId = insertData(4, 1, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testNormalPhleTimeOn() {
        int devId = insertData(4, 1, "phle-time-on");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalDeviceUnavailable() {
        int devId = insertData(4, 1, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalPhleOddOneOut() {
        int devId = insertData(4, 1, "phle-odd-one-out");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalBruteForce() {
        int devId = insertData(4, 1, "brute-force");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousBruteForce() {
        int devId = insertData(4, 2, "brute-force");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousDeviceUnavailable() {
        int devId = insertData(4, 2, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousPhleOddOneOut() {
        int devId = insertData(4, 2, "phle-odd-one-out");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testSuspiciousPhleTimeOn() {
        int devId = insertData(4, 2, "phle-time-on");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousStateReset() {
        int devId = insertData(4, 2, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testAttackStateReset() {
        int devId = insertData(4, 3, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }
}
