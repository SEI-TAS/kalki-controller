package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.database.Postgres;
import edu.cmu.sei.kalki.db.models.Alert;
import edu.cmu.sei.kalki.db.models.AlertType;
import edu.cmu.sei.kalki.db.models.Device;
import edu.cmu.sei.kalki.db.models.DeviceSecurityState;
import edu.cmu.sei.kalki.db.models.DeviceType;
import edu.cmu.sei.kalki.db.models.PolicyCondition;
import edu.cmu.sei.kalki.db.models.PolicyRule;
import edu.cmu.sei.kalki.db.models.StateTransition;
import edu.cmu.sei.kalki.db.utils.TestDB;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class TestBase
{
    protected DeviceType testDeviceType;
    protected Device testDevice;

    protected static MainController mainController;

    @BeforeEach
    public void setup() {
        try {
            testDeviceType = null;
            testDevice = null;

            Postgres.setLoggingLevel(Level.SEVERE);
            TestDB.recreateTestDB();
            TestDB.initialize();

            mainController = new MainController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void reset() {
        try {
            mainController.stopListeners();
            TestDB.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected AlertType insertAlertType(String alertTypeName) {
        AlertType alertType = new AlertType(alertTypeName, "test alert", "test");
        alertType.insert();
        return alertType;
    }

    protected Device insertDeviceData(int stateId) {
        testDeviceType = new DeviceType(1, "Test Type");
        testDeviceType.insert();

        testDevice = new Device("Test Device", "device", testDeviceType, "127.0.0.1", 1, 1);
        testDevice.insert();

        DeviceSecurityState dss = new DeviceSecurityState(testDevice.getId(), stateId);
        dss.insert();

        return testDevice;
    }

    protected void insertDevicePolicy(int startingStateId, int finishStateId, AlertType alertType) {
        List<Integer> alertTypeIds = new ArrayList<>();
        alertTypeIds.add(alertType.getId());
        insertDevicePolicy(startingStateId, finishStateId, alertTypeIds);
    }

    protected void insertDevicePolicy(int startingStateId, int finishStateId, List<Integer> alertTypeIds) {
        PolicyCondition policyCondition = new PolicyCondition(10, alertTypeIds);
        policyCondition.insert();

        StateTransition stateTransition = new StateTransition(startingStateId, finishStateId);
        stateTransition.insert();

        PolicyRule policyRule = new PolicyRule(stateTransition.getId(), policyCondition.getId(), testDeviceType.getId(), 10);
        policyRule.insert();
    }

    protected Alert insertAlert(Device device, AlertType alertType) {
        System.out.println("Inserting test alert of type: " + alertType.getName());
        Alert alert = new Alert(device.getId(), alertType.getName(), alertType.getId(), "this is a test");
        alert.insert();
        return alert;
    }
}
