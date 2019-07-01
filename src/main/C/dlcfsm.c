#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_DLCStateMachine.h"

JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_DLCStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    	    (*env) -> MonitorEnter(env, fsmObj);
        	jclass dlcClass = (*env) -> GetObjectClass(env, fsmObj);
        	jfieldID dlcCurrentStateField = (*env) -> GetFieldID(env, dlcClass, "currentState", "I");
        	jfieldID dlcCurrentEventField = (*env) -> GetFieldID(env, dlcClass, "currentEvent", "Ljava/lang/String;");
        	//get string elements for current event
        	jstring eventJString = (*env) -> GetObjectField(env, fsmObj, dlcCurrentEventField);
        	const char *eventString = (*env)->GetStringUTFChars(env, eventJString, NULL);
        	//get string elements for current state
        	jint dlcCurrentState = (*env)->GetIntField(env, fsmObj, dlcCurrentStateField)
        	;
        	if (dlcCurrentState==0)
        	{
        		if (strcmp(eventString, "dlc-motion-sense")==0)
        		{
        			printf("dlc-motion-senset\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "state-reset")==0)
        		{
        			printf("state-reset event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
        		}
        		else if (strcmp(eventString, "brute-force") == 0)
        		{
        			printf("brute-force event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "default-login")==0)
        		{
        			printf("default-login event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "max-login-attempts")==0)
        		{
        			printf("max-login-attempts\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "device-unavailable")==0)
        		{
        			printf("device-unavailable\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else
        		{
        			printf("incorrect alert type\n");
        		}
        	}
        	else if (dlcCurrentState==2)
        	{
        		if (strcmp(eventString, "state-reset")==0)
        		{
        			printf("state-reset event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
        		}
        		else
        		{
        			printf("incorrect alert type\n");
        		}
        	}
        	else if (dlcCurrentState==1)
        	{
        		if (strcmp(eventString, "max-login-attempts")==0)
        		{
        			printf("max-login-attempts\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "default-login")==0)
        		{
        			printf("default-login event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "state-reset")==0)
        		{
        			printf("state-reset event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, 0);
        		}
        		else if (strcmp(eventString, "brute-force")==0)
        		{
        			printf("brute-force event\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "device-unavailable")==0)
        		{
        			printf("device-unavailable\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
        		}
        		else if (strcmp(eventString, "dlc-motion-sense")==0)
        		{
        			printf("dlc-motion-sense\n");
        			(*env) -> SetIntField(env, fsmObj, dlcCurrentStateField, dlcCurrentState = dlcCurrentState + 1);
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