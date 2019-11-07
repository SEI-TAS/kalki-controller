package edu.cmu.sei.ttg.kalki.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.cmu.sei.ttg.kalki.models.*;
import edu.cmu.sei.ttg.kalki.database.Postgres;

public class TestUNTSStateMachine extends TestIOTController {

    @Test
    public void testNormalBruteForce() {
        int devId = insertData(2, 1, "brute-force");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalUntsAbnormalTraffic() {
        int devId = insertData(2, 1, "unts-abnormal-traffic");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalDeviceUnavailable() {
        int devId = insertData(2, 1, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testNormalUntsGyro() {
        int devId = insertData(2, 1, "unts-gyro");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalUntsAcceleration() {
        int devId = insertData(2, 1, "unts-acceleration");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalUntsMagnetometer() {
        int devId = insertData(2, 1, "unts-magnetometer");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalUntsTemperature() {
        int devId = insertData(2, 1, "unts-temperature");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate()/2);
    }

    @Test
    public void testNormalStateReset() {
        int devId = insertData(2, 1, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testSuspiciousBruteForce() {
        int devId = insertData(2, 2, "brute-force");
        Device d = Postgres.findDevice(devId);
        assertEquals(2, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousUntsAbnormalTraffic() {
        int devId = insertData(2, 2, "unts-abnormal-traffic");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousDeviceUnavailable() {
        int devId = insertData(2, 2, "device-unavailable");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousUntsGyroAvg() {
        int devId = insertData(2, 2, "unts-gyro-avg");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousUntsAccelerationAvg() {
        int devId = insertData(2, 2, "unts-acceleration-avg");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousUntsTemperatureAvg() {
        int devId = insertData(2, 2, "unts-temperature-avg");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousUntsMagnetometerAvg() {
        int devId = insertData(2, 2, "unts-magnetometer-avg");
        Device d = Postgres.findDevice(devId);
        assertEquals(3, d.getCurrentState().getStateId());
    }

    @Test
    public void testSuspiciousStateReset() {
        int devId = insertData(2, 2, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }

    @Test
    public void testAttackStateReset() {
        int devId = insertData(2, 3, "state-reset");
        Device d = Postgres.findDevice(devId);
        assertEquals(1, d.getCurrentState().getStateId());
        assertEquals(d.getSamplingRate(), d.getDefaultSamplingRate());
    }
}
