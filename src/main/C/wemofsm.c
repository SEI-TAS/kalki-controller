#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine.h"
JNIEXPORT int JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState)
{
    (*env) -> MonitorEnter(env, fsmObj);
	char eventString[256];
	    strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));;
	if (currentState==0)
	{
		if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
		{
			printf("wemo-current-me-greater-low\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			return currentState = 0;
		}
		else if (strcmp(eventString, "wemo-today-kwh")==0)
		{
			printf("wemo-today-kwh\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-last-change")==0)
		{
			printf("wemo-last-change\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-same-group")==0)
		{
			printf("wemo-current-mw-same-group\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
		{
			printf("wemo-current-mw-greater-high\n");
			return currentState = currentState + 1;;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-time-on")==0)
		{
			printf("wemo-time-on\n");
			return currentState = currentState + 1;
		}
		else
		{
			printf("incorrect alert type\n");
			return currentState;
		}
	}
	else if (currentState==1)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			return currentState = 0;
		}
		else if (strcmp(eventString, "wemo-time-on")==0)
		{
			printf("wemo-time-on\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
		{
			printf("wemo-current-mw-greater-low\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-today-kwh")==0)
		{
			printf("wemo-today-kwh\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-same-group")==0)
		{
			printf("wemo-current-mw-same-group\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "wemo-last-change")==0)
		{
			printf("wemo-last-change\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
		{
			printf("wemo-current-mw-greater-high\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			return currentState = currentState + 1;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			return currentState = currentState + 1;
		}
		else
		{
			printf("incorrect alert type\n");
			return currentState;
		}
	}
	else if (currentState==2)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			return currentState = 0;
		}
		else
		{
			printf("not reset event\n");
			return currentState;
		}
	}
	else
	{
		printf("incorrect state type\n");
		return currentState;
	}
	(*env) -> MonitorExit(env, fsmObj);
}