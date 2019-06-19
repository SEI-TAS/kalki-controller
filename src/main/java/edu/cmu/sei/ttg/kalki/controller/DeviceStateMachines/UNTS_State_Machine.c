#include <stdio.h>
#include <jni.h>
#include "edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_UNTSStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_UNTSStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Start up monitoring on thread
        //**Equivalent to synchronized(obj) in Java
        (*env) -> MonitorEnter(env, fsmObj);
        //cache variables for class, obj, and fields
        jclass untsClass = (*env) -> GetObjectClass(env, fsmObj);
        jfieldID untsCurrentStateField = (*env) -> GetFieldID(env, untsClass, "currentState", "I");
        jfieldID untsCurrentEventField = (*env) -> GetFieldID(env, untsClass, "currentEvent", "I");
        jint untsCurrentState = (*env) -> GetIntField(env, fsmObj, untsCurrentStateField);
        jint untsCurrentEvent = (*env) -> GetIntField(env, fsmObj, untsCurrentEventField);
        //Switch statement
        printf("Here in c Code: UNTS \n");
        switch(untsCurrentState){
            case 0:
                switch(untsCurrentEvent){
                    case 1: //brute-force
                        printf("brute-force event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 2: //default-login
                        printf("default-login event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 3: //device-unavailable
                        printf("device-unavailable event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 4: //max-login-attempts
                        printf("max-login event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 5: //state-reset
                        printf("state-reset event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
                        break;
                    case 6:
                        printf("unts-acceleration\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 7:
                        printf("unts-gyro\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 8:
                        printf("unts-gyro-secondary\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 9:
                        printf("unts-magnetometer\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 10:
                        printf("unts-magnetometer-online-low\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 11:
                        printf("unts-magnetometer-online-high\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 12:
                        printf("unts-temperature\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 13:
                        printf("unts-temperature-avg\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 14:
                        printf("unts-temperature-online\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;

                    default: //anything else is wrong
                        break;
                }
                break;
            case 1:
                switch(untsCurrentEvent){
                    case 1: //brute-force
                        printf("brute-force event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 2: //default-login
                        printf("default-login event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 3: //device-unavailable
                        printf("device-unavailable event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 4: //max-login-attempts
                        printf("max-login event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 5: //state-reset
                        printf("state-reset event\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
                        break;
                    case 6:
                        printf("unts-acceleration\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 7:
                        printf("unts-gyro\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 8:
                        printf("unts-gyro-secondary\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 9:
                        printf("unts-magnetometer\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 10:
                        printf("unts-magnetometer-online-low\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 11:
                        printf("unts-magnetometer-online-high\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 12:
                        printf("unts-temperature\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 13:
                        printf("unts-temperature-avg\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;
                    case 14:
                        printf("unts-temperature-online\n");
                        (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
                        break;

                    default: //anything else is wrong
                        break;
                }
                break;
            case 2: //in Attack
                //if 'state-reset' set field to 0
                if(untsCurrentEvent == 5){
                    (*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
                    break;
                }
                else {
                    printf("Under Attack still\n");
                    break;
                }
                break;
            default:
                printf("Incorrect currentState, moving to state: Attack\n");
                break;

        }
        (*env) -> MonitorExit(env, fsmObj);
        //Need to dump local values
        (*env) -> DeleteLocalRef(env, fsmObj);

        return;
}