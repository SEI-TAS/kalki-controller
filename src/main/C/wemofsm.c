#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine.h"
JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    (*env) -> MonitorEnter(env, fsmObj);
    	jclass wemoClass = (*env) -> GetObjectClass(env, fsmObj);
    	jfieldID wemoCurrentStateField = (*env) -> GetFieldID(env, wemoClass, "currentState", "I");
    	jfieldID wemoCurrentEventField = (*env) -> GetFieldID(env, wemoClass, "currentEvent", "Ljava/lang/String;");
    	//get string elements for current event
    	jstring eventJString = (*env) -> GetObjectField(env, fsmObj, wemoCurrentEventField);
    	const char *eventString = (*env)->GetStringUTFChars(env, eventJString, NULL);
    	//get string elements for current state
    	jint wemoCurrentState = (*env)->GetIntField(env, fsmObj, wemoCurrentStateField);
    	if (wemoCurrentState==0)
    	{
    		if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
    		}
    		else if (strcmp(eventString, "wemo-today-kwh")==0)
    		{
    			printf("wemo-today-kwh\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-last-change")==0)
    		{
    			printf("wemo-last-change\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "device-unavailable")==0)
    		{
    			printf("device-unavailable\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-current-mw-same-group")==0)
    		{
    			printf("wemo-current-mw-same-group\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "brute-force")==0)
    		{
    			printf("brute-force event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
    		{
    			printf("wemo-current-mw-greater-high\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "default-login")==0)
    		{
    			printf("default-login event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "max-login-attempts")==0)
    		{
    			printf("max-login-attempts\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-time-on")==0)
    		{
    			printf("wemo-time-on\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else
    		{
    			printf("incorrect alert type\n");
    		}
    	}
    	else if (wemoCurrentState==1)
    	{
    		if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
    		}
    		else if (strcmp(eventString, "device-unavailable")==0)
    		{
    			printf("device-unavailable\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
    		{
    			printf("wemo-current-mw-greater-low\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "max-login-attempts")==0)
    		{
    			printf("max-login-attempts\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
    		{
    			printf("wemo-current-mw-greater-high\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "default-login")==0)
    		{
    			printf("default-login event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else if (strcmp(eventString, "brute-force")==0)
    		{
    			printf("brute-force event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, wemoCurrentState = wemoCurrentState + 1);
    		}
    		else
    		{
    			printf("incorrect alert type\n");
    		}
    	}
    	else if (wemoCurrentState==2)
    	{
    		if (strcmp(eventString, "state-reset")==0)
    		{
    			printf("state-reset event\n");
    			(*env) -> SetIntField(env, fsmObj, wemoCurrentStateField, 0);
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