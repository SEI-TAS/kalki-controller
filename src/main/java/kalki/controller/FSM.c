#include <jni.h>
#include <stdio.h>
#include "FSM.h"

JNIEXPORT void JNICALL Java_kalki_controller_WEMO_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Need to get the Class reference for the FSM
    jclass fsmClass = (*env) -> GetObjectClass(env, fsmObj);
    //Need to get the Field IDs of the instance variables
    jfieldID fsmCurrentState = (*env) -> GetFieldID(env, fsmClass, "currentState", "I");
    jfieldID fsmCurrentEvent = (*env) -> GetFieldID(env, fsmClass, "currentEvent", "I");
    //declare the lookup table
                //{"Normal" 0, "Suspicious" 1, "Attack" 2},
               // {"Suspicious" 1, "Attack" 2, "Attack" 2},
                //{"Normal" 0, "Normal" 0, "Normal" 0}
    jint currentState = (*env) -> GetIntField(env, fsmObj, fsmCurrentState);
    jint currentEvent = (*env) -> GetIntField(env, fsmObj, fsmCurrentEvent);
    if (currentEvent == 2 | currentEvent == 1 | currentEvent == 0) {
        int lookup_table[3][3] = {
                {0, 1, 2},
                {1, 2, 2},
                {0, 0, 0}
            };
         int newState = lookup_table[newEvent][currentState];
            //Assign new State
         (*env)->SetIntField(env, fsmObj, fsmCurrentState, newState);

         printf("Successfully set newState %d\n", newState);
    }
    else {
        printf("Incorrect State not setting\n");
        return;
    }
}
JNIEXPORT void JNICALL Java_kalki_controller_DLC_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Need to get the Class reference for the FSM
    jclass fsmClass = (*env) -> GetObjectClass(env, fsmObj);
    //Need to get the Field IDs of the instance variables
    jfieldID fsmCurrentState = (*env) -> GetFieldID(env, fsmClass, "currentState", "I");
    jfieldID fsmCurrentEvent = (*env) -> GetFieldID(env, fsmClass, "currentEvent", "I");
    //declare the lookup table
                //{"Normal" 0, "Suspicious" 1, "Attack" 2},
               // {"Suspicious" 1, "Attack" 2, "Attack" 2},
                //{"Normal" 0, "Normal" 0, "Normal" 0}
    jint currentState = (*env) -> GetIntField(env, fsmObj, fsmCurrentState);
    jint currentEvent = (*env) -> GetIntField(env, fsmObj, fsmCurrentEvent);
    if (currentEvent == 2 | currentEvent == 1 | currentEvent == 0) {
        int lookup_table[3][3] = {
                {0, 1, 2},
                {1, 2, 2},
                {0, 0, 0}
            };
         int newState = lookup_table[newEvent][currentState];
            //Assign new State
         (*env)->SetIntField(env, fsmObj, fsmCurrentState, newState);

         printf("Successfully set newState %d\n", newState);
    }
    else {
        printf("Incorrect State not setting\n");
        return;
    }
}

JNIEXPORT void JNICALL Java_kalki_controller_PHLE_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Need to get the Class reference for the FSM
    jclass fsmClass = (*env) -> GetObjectClass(env, fsmObj);
    //Need to get the Field IDs of the instance variables
    jfieldID fsmCurrentState = (*env) -> GetFieldID(env, fsmClass, "currentState", "I");
    jfieldID fsmCurrentEvent = (*env) -> GetFieldID(env, fsmClass, "currentEvent", "I");
    //declare the lookup table
                //{"Normal" 0, "Suspicious" 1, "Attack" 2},
               // {"Suspicious" 1, "Attack" 2, "Attack" 2},
                //{"Normal" 0, "Normal" 0, "Normal" 0}
    jint currentState = (*env) -> GetIntField(env, fsmObj, fsmCurrentState);
    jint currentEvent = (*env) -> GetIntField(env, fsmObj, fsmCurrentEvent);
    if (currentEvent == 2 | currentEvent == 1 | currentEvent == 0) {
        int lookup_table[3][3] = {
                {0, 1, 2},
                {1, 2, 2},
                {0, 0, 0}
            };
         int newState = lookup_table[newEvent][currentState];
            //Assign new State
         (*env)->SetIntField(env, fsmObj, fsmCurrentState, newState);

         printf("Successfully set newState %d\n", newState);
    }
    else {
        printf("Incorrect State not setting\n");
        return;
    }
}

JNIEXPORT void JNICALL Java_kalki_controller_UNTS_generateNextState(JNIEnv *env, jobject fsmObj)
{
    //Need to get the Class reference for the FSM
    jclass fsmClass = (*env) -> GetObjectClass(env, fsmObj);
    //Need to get the Field IDs of the instance variables
    jfieldID fsmCurrentState = (*env) -> GetFieldID(env, fsmClass, "currentState", "I");
    jfieldID fsmCurrentEvent = (*env) -> GetFieldID(env, fsmClass, "currentEvent", "I");
    //declare the lookup table
                //{"Normal" 0, "Suspicious" 1, "Attack" 2},
               // {"Suspicious" 1, "Attack" 2, "Attack" 2},
                //{"Normal" 0, "Normal" 0, "Normal" 0}
    jint currentState = (*env) -> GetIntField(env, fsmObj, fsmCurrentState);
    jint currentEvent = (*env) -> GetIntField(env, fsmObj, fsmCurrentEvent);
    if (currentEvent == 2 | currentEvent == 1 | currentEvent == 0) {
        int lookup_table[3][3] = {
                {0, 1, 2},
                {1, 2, 2},
                {0, 0, 0}
            };
         int newState = lookup_table[newEvent][currentState];
            //Assign new State
         (*env)->SetIntField(env, fsmObj, fsmCurrentState, newState);

         printf("Successfully set newState %d\n", newState);
    }
    else {
        printf("Incorrect State not setting\n");
        return;
    }
}