#include <stdio.h>
#include <jni.h>
#include "edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_DLCStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_DeviceStateMachines_DLCStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Start up monitoring on thread
    //**Equivalent to synchronized(obj) in Java
    (*env) -> MonitorEnter(env, fsmObj);
    //cache variables for class, obj, and fields
    jclass dlcClass = (*env) -> GetObjectClass(env, fsmObj);
    jfieldID dlcCurrentStateField = (*env) -> GetFieldID(env, dlcClass, "currentState", "I");
    jfieldID dlcCurrentEventField = (*env) -> GetFieldID(env, dlcClass, "currentEvent", "I");
    jint dlcCurrentState = (*env) -> GetIntField(env, fsmObj, dlcCurrentStateField);
    jint dlcCurrentEvent = (*env) -> GetIntField(env, fsmObj, dlcCurrentEventField);
    //Switch statement
    printf("Here in c Code: DLC \n");
    switch(dlcCurrentState){
        case 0: //Normal
            switch(dlcCurrentEvent){
                case 1: //brute-force
                    printf("brute-force event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 2: //default-login
                    printf("default-login event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 3: //device-unavailable
                    printf("device-unavailable event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 4: //max-login-attempts
                    printf("max-login event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 5: //state-reset
                    printf("state-reset event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
                    break;
                case 15: //dlc-motion-sense
                    printf("dlc-motion-sense event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                default: //anything else is wrong
                    break;
            }
            break;
        case 1: //Attack
            switch(dlcCurrentEvent){
                case 1: //brute-force
                    printf("brute-force event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 2: //default-login
                    printf("default-login event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 3: //device-unavailable
                    printf("device-unavailable event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 4: //max-login-attempts
                    printf("max-login event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                case 5: //state-reset
                    printf("state-reset event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
                    break;
                case 15: //dlc-motion-sense
                    printf("dlc-motion-sense event\n");
                    (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
                    break;
                default: //anything else is wrong
                    break;
            }
            break;
        case 2: //in Attack
            //if 'state-reset' set field to 0
            if(dlcCurrentEvent == 5){
                //set current State to 0
                (*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
                break;
            }
            else {
                printf("Under Attack still \n");
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