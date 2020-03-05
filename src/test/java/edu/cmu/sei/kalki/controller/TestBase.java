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
    private static MainController mainController;

    @BeforeEach
    public void setup() {
        try {
            Postgres.setLoggingLevel(Level.SEVERE);
            TestDB.recreateTestDB();
            TestDB.initialize();

            mainController = new MainController();
            mainController.initListeners();
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

    protected Device insertDeviceData(int stateId, AlertType alertType) {
        DeviceType deviceType = new DeviceType(1, "Test Type");
        deviceType.insert();

        Device device = new Device("Test Device", "device", deviceType, "127.0.0.1", 1, 1);
        device.insert();

        DeviceSecurityState dss = new DeviceSecurityState(device.getId(), stateId);
        dss.insert();

        // Rule 1
        List<Integer> alertTypeIds = new ArrayList<>();
        alertTypeIds.add(alertType.getId());
        PolicyCondition policyCondition = new PolicyCondition(10, alertTypeIds);
        policyCondition.insert();

        StateTransition stateTransition = new StateTransition(1, 2);
        stateTransition.insert();

        PolicyRule policyRule = new PolicyRule(stateTransition.getId(), policyCondition.getId(), deviceType.getId(), 10);
        policyRule.insert();

        // Rule 2, same but from state 2 to 3
        stateTransition = new StateTransition(2, 3);
        stateTransition.insert();

        policyRule = new PolicyRule(stateTransition.getId(), policyCondition.getId(), deviceType.getId(), 10);
        policyRule.insert();

        return device;
    }

    protected void insertAlert(Device device, AlertType alertType) {
        System.out.println("Inserting test alert of type: " + alertType.getName());
        Alert alert = new Alert(device.getId(), alertType.getName(), alertType.getId(), "this is a test");
        alert.insert();
    }

    protected void waitForNotification() {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
