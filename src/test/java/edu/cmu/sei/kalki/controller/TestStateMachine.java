package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.AlertDAO;
import edu.cmu.sei.kalki.db.daos.DeviceDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.cmu.sei.kalki.db.models.*;

import java.util.ArrayList;
import java.util.List;

public class TestStateMachine extends TestBase
{
    @Test
    public void testNormalAlertTrigger() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId);
        insertDevicePolicy(1, 2, alertType);

        // Alert to trigger notification.
        Alert alert = insertAlert(device, alertType);
        mainController.handleNewInsertion(alert.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(2, device.getCurrentState().getStateId());
    }

    @Test
    public void testNoAlertTrigger() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId);
        insertDevicePolicy(1, 2, alertType);

        // Insert alert type that shouldn't trigger anything.
        alertTypeName = "test-alert-type-NOTUSED";
        alertType = insertAlertType(alertTypeName);

        // Alert to NOT trigger notification.
        Alert alert = insertAlert(device, alertType);
        mainController.handleNewInsertion(alert.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(startingStateId, device.getCurrentState().getStateId());
    }

    @Test
    public void testAlertRemoved() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId);
        insertDevicePolicy(1, 2, alertType);

        // Alert to trigger notification, deleting right away.
        Alert alert = insertAlert(device, alertType);
        AlertDAO.deleteAlert(alert.getId());

        mainController.handleNewInsertion(alert.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(startingStateId, device.getCurrentState().getStateId());
    }

    @Test
    public void testCachedStateMachine() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId);
        insertDevicePolicy(1, 2, alertType);

        String alertTypeName2 = "test-alert-type2";
        AlertType alertType2 = insertAlertType(alertTypeName2);
        insertDevicePolicy(2, 3, alertType2);

        // Alert to trigger notification.
        Alert alert1 = insertAlert(device, alertType);
        mainController.handleNewInsertion(alert1.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(2, device.getCurrentState().getStateId());

        // Do another alert for the same device.
        Alert alert2 = insertAlert(device, alertType2);
        mainController.handleNewInsertion(alert2.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(3, device.getCurrentState().getStateId());
    }

    @Test
    public void testMultipleAlertsCondition() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type1";
        AlertType alertType = insertAlertType(alertTypeName);
        String alertTypeName2 = "test-alert-type2";
        AlertType alertType2 = insertAlertType(alertTypeName2);
        Device device = insertDeviceData(startingStateId);

        List<Integer> alertTypeIds = new ArrayList<>();
        alertTypeIds.add(alertType.getId());
        alertTypeIds.add(alertType2.getId());
        insertDevicePolicy(1, 2, alertTypeIds);

        // Alert to trigger notification.
        Alert alert1 = insertAlert(device, alertType);
        mainController.handleNewInsertion(alert1.getId());

        Alert alert2 = insertAlert(device, alertType2);
        mainController.handleNewInsertion(alert2.getId());

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(2, device.getCurrentState().getStateId());
    }
}
