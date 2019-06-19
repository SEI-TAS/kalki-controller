#include <stdio.h>
#include <jni.h>
#include "edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_PHLEStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_PHLEStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Start up monitoring on thread
        //**Equivalent to synchronized(obj) in Java
        (*env) -> MonitorEnter(env, fsmObj);
        //cache variables for class, obj, and fields
        jclass phleClass = (*env) -> GetObjectClass(env, fsmObj);
        jfieldID phleCurrentStateField = (*env) -> GetFieldID(env, phleClass, "currentState", "I");
        jfieldID phleCurrentEventField = (*env) -> GetFieldID(env, phleClass, "currentEvent", "I");
        jint phleCurrentState = (*env) -> GetIntField(env, fsmObj, phleCurrentStateField);
        jint phleCurrentEvent = (*env) -> GetIntField(env, fsmObj, phleCurrentEventField);
        //Switch statement
        printf("Here in c Code: PHLE \n");
        switch(phleCurrentState){
            case 0:
                switch(phleCurrentEvent){
                    case 1: //brute-force
                        printf("brute-force event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 2: //default-login
                        printf("default-login event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 3: //device-unavailable
                        printf("device-unavailable event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 4: //max-login-attempts
                        printf("max-login event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 5: //state-reset
                        printf("state-reset event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
                        break;
                    case 16:
                        printf("phle-time-off\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 17:
                        printf("phle-odd-one-out\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    default: //anything else is wrong
                        printf("incorrect alert type\n");
                        break;
                }
                break;
            case 1:
                switch(phleCurrentEvent){
                    case 1: //brute-force
                        printf("brute-force event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 2: //default-login
                        printf("default-login event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 3: //device-unavailable
                        printf("device-unavailable event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 4: //max-login-attempts
                        printf("max-login event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 5: //state-reset
                        printf("state-reset event\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
                        break;
                    case 16:
                        printf("phle-time-off\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    case 17:
                        printf("phle-odd-one-out\n");
                        (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
                        break;
                    default: //anything else is wrong
                        printf("incorrect alert type\n");
                        break;
                }
                break;
            case 2: //in Attack
                //if 'state-reset' set field to 0
                if(phleCurrentEvent == 5){
                    //set current State to 0
                    printf("state-reset from Attack\n");
                    (*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
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