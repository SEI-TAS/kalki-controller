package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.AlertTypeDAO;
import edu.cmu.sei.kalki.db.daos.DeviceDAO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.cmu.sei.kalki.db.models.*;

public class TestStateMachine extends TestBase
{
    @Test
    public void testNormalAlertTrigger() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId, alertType);

        // Alert to trigger notification.
        insertAlert(device, alertType);
        waitForNotification();

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(2, device.getCurrentState().getStateId());
    }

    @Test
    public void testNoAlertTrigger() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId, alertType);

        // Insert alert type that shouldn't trigger anything.
        alertTypeName = "test-alert-type-NOTUSED";
        alertType = insertAlertType(alertTypeName);

        // Alert to NOT trigger notification.
        insertAlert(device, alertType);
        waitForNotification();

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(startingStateId, device.getCurrentState().getStateId());
    }

    @Test
    public void testAlertRemoved() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId, alertType);

        // Alert to trigger notification, deleting right away.
        insertAlert(device, alertType);
        AlertTypeDAO.deleteAlertType(alertType.getId());

        waitForNotification();

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(startingStateId, device.getCurrentState().getStateId());
    }

    @Test
    public void testCachedStateMachine() {
        // Set up data.
        int startingStateId = 1;
        String alertTypeName = "test-alert-type";
        AlertType alertType = insertAlertType(alertTypeName);
        Device device = insertDeviceData(startingStateId, alertType);

        // Alert to trigger notification.
        insertAlert(device, alertType);
        waitForNotification();

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(2, device.getCurrentState().getStateId());

        // Do another alert for the same device.
        insertAlert(device, alertType);
        waitForNotification();

        device = DeviceDAO.findDevice(device.getId());
        assertEquals(3, device.getCurrentState().getStateId());
    }
}
