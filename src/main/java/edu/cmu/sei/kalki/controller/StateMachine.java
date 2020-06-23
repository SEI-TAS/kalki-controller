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
            StateTransition transition = StateTransitionDAO.findStateTransition(rule.getStateTransId());
            if(transition.getStartStateId() == currentState.getId()) {
                PolicyCondition condition = PolicyConditionDAO.findPolicyCondition(rule.getPolicyCondId());
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
