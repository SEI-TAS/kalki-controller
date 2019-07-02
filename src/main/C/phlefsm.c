#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_PHLEStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_PHLEStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    (*env) -> MonitorEnter(env, fsmObj);
    	jclass phleClass = (*env) -> GetObjectClass(env, fsmObj);
    	if(phleClass == NULL){
    	    printf("class obj returned null\n");
    	    return;
    	}
    	jfieldID phleCurrentStateField = (*env) -> GetFieldID(env, phleClass, "currentState", "I");
    	jfieldID phleCurrentEventField = (*env) -> GetFieldID(env, phleClass, "currentEvent", "Ljava/lang/String;");
    	if(phleCurrentStateField == NULL | phleCurrentEventField == NULL){
    	            printf("class field IDS returned null\n");
    	            return;
    	}
    	//get string elements for current event
    	jstring eventJString = (*env) -> GetObjectField(env, fsmObj, phleCurrentEventField);
    	const char *eventString = (*env)->GetStringUTFChars(env, eventJString, 0);
    	//get string elements for current state
    	jint phleCurrentState = (*env)->GetIntField(env, fsmObj, phleCurrentStateField);
    	if (phleCurrentState==0)
    	{
    		if (strcmp(eventString, "brute-force")==0)
    		{
    			printf("brute-force event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "device-unavailable")==0)
    		{
    			printf("device-unavailable\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "max-login-attempts")==0)
    		{
    			printf("max-login-attempts\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "phle-odd-one-out")==0)
    		{
    			printf("phle-odd-one-out\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "phle-time-off")==0)
    		{
    			printf("phle-time-off\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
    		}
    		else if (strcmp(eventString, "default-login")==0)
    		{
    			printf("default-login event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else
    		{
    			printf("incorrect alert type\n");
    		}
    	}
    	else if (phleCurrentState==2)
    	{
    		if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
    		}
    		else
    		{
    			printf("not reset event\n");
    		}
    	}
    	else if (phleCurrentState==1)
    	{
    		if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, 0);
    		}
    		else if (strcmp(eventString, "max-login-attempts")==0)
    		{
    			printf("max-login-attempts\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "phle-time-off")==0)
    		{
    			printf("phle-time-ff\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "device-unavailable")==0)
    		{
    			printf("device-unavailable\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "default-login")==0)
    		{
    			printf("default-login event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "phle-odd-one-out")==0)
    		{
    			printf("phle-odd-one-out\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else if (strcmp(eventString, "brute-force")==0)
    		{
    			printf("brute-force event\n");
    			(*env) -> SetIntField(env, fsmObj, phleCurrentStateField, phleCurrentState = phleCurrentState + 1);
    		}
    		else
    		{
    			printf("incorrect alert type\n");
    		}
    	}
    	else
    	{
    		printf("incorrect state type\n");
    	}
    	(*env) -> ReleaseStringUTFChars(env, eventJString, eventString);
    	(*env) -> MonitorExit(env, fsmObj);
    	//Need to dump local values
    	(*env) -> DeleteLocalRef(env, fsmObj);
}