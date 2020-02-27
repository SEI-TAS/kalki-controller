package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.DeviceTypeDAO;
import edu.cmu.sei.kalki.db.database.Postgres;
import edu.cmu.sei.kalki.db.models.*;
import edu.cmu.sei.kalki.db.utils.Config;
import edu.cmu.sei.kalki.db.utils.TestDB;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class TestBase
{
    @BeforeAll
    public static void createTestDB() {
        try {
            // Load config, and overwrite it so that Postgres will create a test DB.
            Config.load("config.json");
            TestDB.overwriteDBConfig();
            Postgres.initializeFromConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void reset() {
        Postgres.resetDatabase();
    }

    protected int insertData(int deviceTypeId, int stateId, String alertType) {
        Device d = new Device("Test Device", "device", DeviceTypeDAO.findDeviceType(deviceTypeId), "127.0.0.1", 1, 1);
        d.insert();

        DeviceSecurityState dss = new DeviceSecurityState(d.getId(), stateId);
        dss.insert();

        AlertType at = new AlertType(alertType, "test alert", "test");
        at.insert();

        System.out.println("Inserting test alert of type: " + at.getName());
        Alert alert = new Alert(d.getId(), at.getName(), at.getId(), "this is a test");
        alert.insert();

        return d.getId();
    }
}
