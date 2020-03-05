package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.DeviceTypeDAO;
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
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class TestBase
{
    @BeforeAll
    public static void setup() {
        try {
            Postgres.setLoggingLevel(Level.SEVERE);
            TestDB.recreateTestDB();
            TestDB.initialize();

            MainController mainController = new MainController();
            mainController.initListeners();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void reset() {
        try {
            TestDB.cleanup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected int insertData(int stateId, String alertType) {
        DeviceType deviceType = new DeviceType(1, "Test Type");
        deviceType.insert();

        Device d = new Device("Test Device", "device", deviceType, "127.0.0.1", 1, 1);
        d.insert();

        DeviceSecurityState dss = new DeviceSecurityState(d.getId(), stateId);
        dss.insert();

        AlertType at = new AlertType(alertType, "test alert", "test");
        at.insert();

        List<Integer> alertTypeIds = new ArrayList<>();
        alertTypeIds.add(at.getId());
        PolicyCondition policyCondition = new PolicyCondition(1, alertTypeIds);
        policyCondition.insert();

        StateTransition stateTransition = new StateTransition(stateId, stateId + 1);
        stateTransition.insert();

        PolicyRule policyRule = new PolicyRule(stateTransition.getId(), policyCondition.getId(), deviceType.getId(), 10);
        policyRule.insert();

        System.out.println("Inserting test alert of type: " + at.getName());
        Alert alert = new Alert(d.getId(), at.getName(), at.getId(), "this is a test");
        alert.insert();

        return d.getId();
    }
}
