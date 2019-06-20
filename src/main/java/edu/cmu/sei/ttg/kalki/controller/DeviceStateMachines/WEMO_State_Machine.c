#include <stdio.h>
#include <jni.h>
#include "edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_WEMOStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_WEMOStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Start up monitoring on thread
            //**Equivalent to synchronized(obj) in Java
            (*env) -> MonitorEnter(env, fsmObj);
            //cache variables for class, obj, and fields
            jclass wemoClass = (*env) -> GetObjectClass(env, fsmObj);
            jfieldID wemoCurrentStateField = (*env) -> GetFieldID(env, wemoClass, "currentState", "I");
            jfieldID wemoCurrentEventField = (*env) -> GetFieldID(env, wemoClass, "currentEvent", "I");
            jint wemoCurrentState = (*env) -> GetIntField(env, fsmObj, wemoCurrentStateField);
            jint wemoCurrentEvent = (*env) -> GetIntField(env, fsmObj, wemoCurrentEventField);
            //Switch statement
            printf("Here in c Code: WEMO \n");
            switch(wemoCurrentState){
                case 0:
                    switch(wemoCurrentEvent){
                        case 1: //brute-force
                            printf("brute-force event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 2: //default-login
                            printf("default-login event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 3: //device-unavailable
                            printf("device-unavailable event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 4: //max-login-attempts
                            printf("max-login event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 5: //state-reset
                            printf("state-reset event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
                        case 18:
                            printf("wemo-current-mw-greater-low\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 19:
                            printf("wemo-current-mw-greater-high\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 20:
                            printf("wemo-current-mw-same-group\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 21:
                            printf("wemo-last-change\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 22:
                            printf("wemo-time-on\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 23:
                            printf("wemo-today-kwh\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        default: //anything else is wrong
                            break;
                    }
                    break;
                case 1:
                    switch(wemoCurrentEvent){
                        case 1: //brute-force
                            printf("brute-force event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 2: //default-login
                            printf("default-login event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 3: //device-unavailable
                            printf("device-unavailable event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 4: //max-login-attempts
                            printf("max-login event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 5: //state-reset
                            printf("state-reset event\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
                        case 18:
                            printf("wemo-current-mw-greater-low\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 19:
                            printf("wemo-current-mw-greater-high\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 20:
                            printf("wemo-current-mw-same-group \n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 21:
                            printf("wemo-last-change\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 22:
                            printf("wemo-time-on\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        case 23:
                            printf("wemo-today-kwh\n");
                            (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
                            break;
                        default: //anything else is wrong
                            break;
                    }
                    break;
                case 2: //in Attack
                    //if 'state-reset' set field to 0
                    if(wemoCurrentEvent == 5){
                        (*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
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