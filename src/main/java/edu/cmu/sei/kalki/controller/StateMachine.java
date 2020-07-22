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
import edu.cmu.sei.kalki.db.daos.AlertTypeDAO;
import edu.cmu.sei.kalki.db.daos.PolicyConditionDAO;
import edu.cmu.sei.kalki.db.daos.PolicyRuleDAO;
import edu.cmu.sei.kalki.db.daos.SecurityStateDAO;
import edu.cmu.sei.kalki.db.daos.StateTransitionDAO;
import edu.cmu.sei.kalki.db.models.Alert;
import edu.cmu.sei.kalki.db.models.AlertType;
import edu.cmu.sei.kalki.db.models.Device;
import edu.cmu.sei.kalki.db.models.DeviceSecurityState;
import edu.cmu.sei.kalki.db.models.PolicyCondition;
import edu.cmu.sei.kalki.db.models.PolicyRule;
import edu.cmu.sei.kalki.db.models.PolicyRuleLog;
import edu.cmu.sei.kalki.db.models.SecurityState;
import edu.cmu.sei.kalki.db.models.StageLog;
import edu.cmu.sei.kalki.db.models.StateTransition;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

public class StateMachine {
    protected static final Logger logger = Logger.getLogger(StateMachine.class.getName());

    private Device device;
    private SecurityState currentState;
    private List<PolicyRule> policyRules;

    /**
     * Stores the device info, gets details about its current state, and loads all policy rules associated with the
     * device type of this device.
     * @param device
     */
    public StateMachine(Device device) {
        this.device = device;
        this.currentState = SecurityStateDAO.findSecurityState(device.getCurrentState().getStateId());
        this.policyRules = PolicyRuleDAO.findPolicyRules(device.getType().getId());
    }

    /**
     * If there is a state change associated to the given alert, execute it.
     * @param newAlert
     */
    public void executeStateChangeIfNeeded(Alert newAlert){
        AlertType newAlertType = AlertTypeDAO.findAlertType(newAlert.getAlertTypeId());
        logger.info("Alert: " + newAlertType.getName() + ", Device Type: " + device.getType().getName() + ", Current State: " + currentState.getName());

        // Look in all policy rules for the ones that are triggered by this alert type, on our current state.
        for(PolicyRule rule : policyRules) {
            // First check if this rule applies to this state as its starting point. Ignore otherwise.
            StateTransition transition = StateTransitionDAO.findStateTransition(rule.getStateTransitionId());
            if(transition.getStartStateId() == currentState.getId()) {
                PolicyCondition condition = PolicyConditionDAO.findPolicyCondition(rule.getPolicyConditionId());
                if(conditionHasBeenMet(condition, newAlert.getTimestamp())) {
                    // We should only get here if alerts for all type in the condition for this rule have been stored in the last time.
                    logger.info("Condition was met, executing policy rule.");
                    executePolicyRule(rule, transition);

                    // If the policy executed (we were in the right state), stop checking other rules.
                    return;
                }
            }
        }

        logger.info("No matching policy rule found.");
    }

    /**
     * Check that ALL alerts for this condition have been triggered in the given threshold.
     * @param condition
     * @return
     */
    private boolean conditionHasBeenMet(PolicyCondition condition, Timestamp lastAlertTimestamp) {
        List<Integer> conditionAlertTypeIds = condition.getAlertTypeIds();
        logger.info("Ids for current condition: " + conditionAlertTypeIds.toString());
        int threshold = condition.getThreshold();
        for(int alertTypeId : conditionAlertTypeIds) {
            // Check that all alerts have been triggered.
            List<Alert> alerts = AlertDAO.findAlertsOverTime(device.getId(), alertTypeId, lastAlertTimestamp, threshold, "minutes");
            logger.info("Found " + alerts.size() + " alerts of type " + alertTypeId + " over the last " + threshold + " minutes.");
            if(alerts.size() == 0) {
                // If no alerts for one of the conditions were found, the condition has not been met.
                logger.info("Condition was not met.");
                return false;
            }
        }

        return true;
    }

    /**
     * Executes a triggered policy rule, if it applies to the current state.
     * @param rule
     */
    private void executePolicyRule(PolicyRule rule, StateTransition transition) {
        // Update the device and its associated info in the DB, changing its state.
        int finalSecStateId = transition.getFinishStateId();
        int samplingRateFactor = rule.getSamplingRateFactor();
        executeStateChange(finalSecStateId, samplingRateFactor);

        // Store the fact that this rule was triggered.
        PolicyRuleLog log = new PolicyRuleLog(rule.getId(), device.getId());
        log.insert();
    }

    /**
     * Updates a device security state and sampling rate in the DB.
     * @param newStateId
     * @param newSamplingRateFactor
     */
    private void executeStateChange(int newStateId, int newSamplingRateFactor) {
        SecurityState newState = SecurityStateDAO.findSecurityState(newStateId);
        logger.info("New State: " + newState.getName());
        DeviceSecurityState newDeviceSecurityState = new DeviceSecurityState(device.getId(), newState.getId());
        newDeviceSecurityState.insert();

        logger.info("New Sampling Rate Factor: " + newSamplingRateFactor);
        int newSamplingRate = device.getDefaultSamplingRate() * newSamplingRateFactor;
        logger.info("New Sampling Rate: " + newSamplingRate);
        device.setSamplingRate(newSamplingRate);
        device.setCurrentState(newDeviceSecurityState);
        device.insertOrUpdate();

        currentState = newState;

        StageLog log = new StageLog(device.getCurrentState().getId(), StageLog.Action.OTHER, StageLog.Stage.TRIGGER, "Updated device: " + device.getId());
        log.insert();
    }
}
