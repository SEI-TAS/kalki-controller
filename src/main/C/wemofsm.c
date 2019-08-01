#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine.h"
JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_WEMOStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate)
{
	char eventString[256];
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));
	int newCurrentState;
    int newSamplingRate = samplingRate;
	if (currentState==2)
	{
		if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
		{
			printf("wemo-current-me-greater-low\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
		}
		else if (strcmp(eventString, "wemo-today-kwh")==0)
		{
			printf("wemo-today-kwh\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-last-change")==0)
		{
			printf("wemo-last-change\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-same-group")==0)
		{
			printf("wemo-current-mw-same-group\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
		{
			printf("wemo-current-mw-greater-high\n");
			newCurrentState = currentState + 1;;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-time-on")==0)
		{
			printf("wemo-time-on\n");
			newCurrentState = currentState + 1;
		}
		else
		{
			printf("incorrect alert type\n");
			newCurrentState;
		}
	}
	else if (currentState==1)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate/2;
		}
		else if (strcmp(eventString, "wemo-time-on")==0)
		{
			printf("wemo-time-on\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
		{
			printf("wemo-current-mw-greater-low\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "wemo-today-kwh")==0)
		{
			printf("wemo-today-kwh\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "wemo-current-mw-same-group")==0)
		{
			printf("wemo-current-mw-same-group\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "wemo-last-change")==0)
		{
			printf("wemo-last-change\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
		{
			printf("wemo-current-mw-greater-high\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState + 1;
			newSamplingRate = samplingRate*2;
		}
		else
		{
			printf("incorrect alert type\n");
			newCurrentState;
		}
	}
	else if (currentState==3)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate/2;
		}
		else
		{
			printf("not reset event\n");
			newCurrentState;
		}
	}
	else
	{
		printf("incorrect state type\n");
		newCurrentState;
	}
	//build returnArray
	int cArray[2];
    cArray[0] = newCurrentState;
    cArray[1] = newSamplingRate;
    jintArray returnArray = (*env) ->NewIntArray(env, 2);
    (*env) -> SetIntArrayRegion(env, returnArray, 0, 2, cArray);
    return (returnArray);
}