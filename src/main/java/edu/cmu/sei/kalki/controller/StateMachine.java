package edu.cmu.sei.kalki.controller;

import edu.cmu.sei.kalki.db.daos.PolicyConditionDAO;
import edu.cmu.sei.kalki.db.daos.PolicyRuleDAO;
import edu.cmu.sei.kalki.db.daos.SecurityStateDAO;
import edu.cmu.sei.kalki.db.daos.StateTransitionDAO;
import edu.cmu.sei.kalki.db.models.AlertType;
import edu.cmu.sei.kalki.db.models.Device;
import edu.cmu.sei.kalki.db.models.DeviceSecurityState;
import edu.cmu.sei.kalki.db.models.PolicyCondition;
import edu.cmu.sei.kalki.db.models.PolicyRule;
import edu.cmu.sei.kalki.db.models.PolicyRuleLog;
import edu.cmu.sei.kalki.db.models.SecurityState;
import edu.cmu.sei.kalki.db.models.StageLog;
import edu.cmu.sei.kalki.db.models.StateTransition;

import java.util.List;

public class StateMachine {
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
     * @param alertType
     */
    public void executeStateChange(AlertType alertType){
        System.out.println("Alert: " + alertType.getName() + ", Device Type: " + device.getType().getName() + ", Current State: " + currentState.getName());

        // Look in all policy rules for the ones that are triggered by this alert type, on our current state.
        for(PolicyRule rule : policyRules) {
            PolicyCondition condition = PolicyConditionDAO.findPolicyCondition(rule.getPolicyCondId());

            // TODO: Assuming only one alert per condition for now. Modify this to handle the concept of multiple alerts
            // "at the same time".
            if(condition.getAlertTypeIds().contains(alertType.getId())) {
                StateTransition transition = StateTransitionDAO.findStateTransition(rule.getStateTransId());
                if(transition.getStartStateId() == currentState.getId()) {
                    System.out.println("Matching policy rule found.");
                    int finalSecStateId = transition.getFinishStateId();
                    int samplingRate = rule.getSamplingRate();
                    this.updateDeviceInDB(finalSecStateId, samplingRate);

                    // Store the fact that this rule was triggered.
                    PolicyRuleLog log = new PolicyRuleLog(rule.getId(), device.getId());
                    log.insert();

                    // Assuming only one policy rule for the given alert and starting state, this would be the only
                    // matching case.
                    return;
                }
            }
        }

        System.out.println("No matching policy rule found.");
    }

    /**
     * Updates a device security state and sampling rate in the DB.
     * @param newStateId
     * @param newSamplingRate
     */
    private void updateDeviceInDB(int newStateId, int newSamplingRate) {
        SecurityState newState = SecurityStateDAO.findSecurityState(newStateId);
        System.out.println("New State: " + newState.getName());
        DeviceSecurityState newDeviceSecurityState = new DeviceSecurityState(device.getId(), newState.getId());
        newDeviceSecurityState.insert();

        System.out.println("Sampling Rate:" + newSamplingRate);
        device.setCurrentState(newDeviceSecurityState);
        device.setSamplingRate(newSamplingRate);
        device.insertOrUpdate();

        currentState = newState;

        StageLog log = new StageLog(device.getCurrentState().getId(), StageLog.Action.OTHER, StageLog.Stage.TRIGGER, "Updated device: " + device.getId());
        log.insert();
    }
}
