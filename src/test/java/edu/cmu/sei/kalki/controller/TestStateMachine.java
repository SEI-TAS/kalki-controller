/*
 * Kalki - A Software-Defined IoT Security Platform
 * Copyright 2020 Carnegie Mellon University.
 * NO WARRANTY. THIS CARNEGIE MELLON UNIVERSITY AND SOFTWARE ENGINEERING INSTITUTE MATERIAL IS FURNISHED ON AN "AS-IS" BASIS. CARNEGIE MELLON UNIVERSITY MAKES NO WARRANTIES OF ANY KIND, EITHER EXPRESSED OR IMPLIED, AS TO ANY MATTER INCLUDING, BUT NOT LIMITED TO, WARRANTY OF FITNESS FOR PURPOSE OR MERCHANTABILITY, EXCLUSIVITY, OR RESULTS OBTAINED FROM USE OF THE MATERIAL. CARNEGIE MELLON UNIVERSITY DOES NOT MAKE ANY WARRANTY OF ANY KIND WITH RESPECT TO FREEDOM FROM PATENT, TRADEMARK, OR COPYRIGHT INFRINGEMENT.
 * Released under a MIT (SEI)-style license, please see license.txt or contact permission@sei.cmu.edu for full terms.
 * [DISTRIBUTION STATEMENT A] This material has been approved for public release and unlimited distribution.  Please see Copyright notice for non-US Government use and distribution.
 * This Software includes and/or makes use of the following Third-Party Software subject to its own license:
 * 1. Google Guava (https://github.com/google/guava) Copyright 2007 The Guava Authors.
 * 2. JSON.simple (https://code.google.com/archive/p/json-simple/) Copyright 2006-2009 Yidong Fang, Chris Nokleberg.
 * 3. JUnit (https://junit.org/junit5/docs/5.0.1/api/overview-summary.html) Copyright 2020 The JUnit Team.
 * 4. Play Framework (https://www.playframework.com/) Copyright 2020 Lightbend Inc..
 * 5. PostgreSQL (https://opensource.org/licenses/postgresql) Copyright 1996-2020 The PostgreSQL Global Development Group.
 * 6. Jackson (https://github.com/FasterXML/jackson-core) Copyright 2013 FasterXML.
 * 7. JSON (https://www.json.org/license.html) Copyright 2002 JSON.org.
 * 8. Apache Commons (https://commons.apache.org/) Copyright 2004 The Apache Software Foundation.
 * 9. RuleBook (https://github.com/deliveredtechnologies/rulebook/blob/develop/LICENSE.txt) Copyright 2020 Delivered Technologies.
 * 10. SLF4J (http://www.slf4j.org/license.html) Copyright 2004-2017 QOS.ch.
 * 11. Eclipse Jetty (https://www.eclipse.org/jetty/licenses.html) Copyright 1995-2020 Mort Bay Consulting Pty Ltd and others..
 * 12. Mockito (https://github.com/mockito/mockito/wiki/License) Copyright 2007 Mockito contributors.
 * 13. SubEtha SMTP (https://github.com/voodoodyne/subethasmtp) Copyright 2006-2007 SubEthaMail.org.
 * 14. JSch - Java Secure Channel (http://www.jcraft.com/jsch/) Copyright 2002-2015 Atsuhiko Yamanaka, JCraft,Inc. .
 * 15. ouimeaux (https://github.com/iancmcc/ouimeaux) Copyright 2014 Ian McCracken.
 * 16. Flask (https://github.com/pallets/flask) Copyright 2010 Pallets.
 * 17. Flask-RESTful (https://github.com/flask-restful/flask-restful) Copyright 2013 Twilio, Inc..
 * 18. libvirt-python (https://github.com/libvirt/libvirt-python) Copyright 2016 RedHat, Fedora project.
 * 19. Requests: HTTP for Humans (https://github.com/psf/requests) Copyright 2019 Kenneth Reitz.
 * 20. netifaces (https://github.com/al45tair/netifaces) Copyright 2007-2018 Alastair Houghton.
 * 21. ipaddress (https://github.com/phihag/ipaddress) Copyright 2001-2014 Python Software Foundation.
 * DM20-0543
 *
 */
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
