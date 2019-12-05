#include <stdio.h>
#include <string.h>
#include "../../fsm/headers/edu_cmu_sei_kalki_controller_fsm_WEMOStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_kalki_controller_fsm_WEMOStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate, jint defaultSamplingRate)
{
	char eventString[256];
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));
	int newCurrentState;
    int newSamplingRate = samplingRate;
    if (currentState==1)
    {
        if (strcmp(eventString, "state-reset")==0)
        {
            printf("state-reset event\n");
            newCurrentState = 1;
            newSamplingRate = defaultSamplingRate;
        }
        else if (strcmp(eventString, "wemo-time-on")==0)
        {
            printf("wemo-time-on\n");
            newCurrentState = currentState + 1;
            newSamplingRate = samplingRate;
        }
        else if (strcmp(eventString, "device-unavailable")==0)
        {
            printf("device-unavailable\n");
            newCurrentState = currentState + 1;
            newSamplingRate = samplingRate;
        }
        else if (strcmp(eventString, "wemo-current-mw-greater-low")==0)
        {
            printf("wemo-current-mw-greater-low\n");
            newCurrentState = currentState + 1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "wemo-today-kwh")==0)
        {
            printf("wemo-today-kwh\n");
            newCurrentState = currentState + 1;
            newSamplingRate = samplingRate;
        }
        else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
        {
            printf("wemo-current-mw-greater-high\n");
            newCurrentState = currentState + 2;
            newSamplingRate = samplingRate;
        }
        else
        {
            printf("incorrect alert type\n");
            newCurrentState;
            newSamplingRate = samplingRate;
        }
    }
	else if (currentState==2)
	{
	    newSamplingRate = samplingRate;
		if (strcmp(eventString, "wemo-current-mw-greater-low-suspicious")==0)
		{
			printf("wemo-current-me-greater-low\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = defaultSamplingRate;
		}
		else if (strcmp(eventString, "wemo-today-kwh")==0)
		{
			printf("wemo-today-kwh\n");
			newCurrentState = currentState;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState + 1;
		}
		else if (strcmp(eventString, "wemo-current-mw-greater-high")==0)
		{
			printf("wemo-current-mw-greater-high\n");
			newCurrentState = currentState + 1;;
		}
		else if (strcmp(eventString, "wemo-time-on")==0)
		{
			printf("wemo-time-on\n");
			newCurrentState = currentState;
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
			newSamplingRate = defaultSamplingRate;
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
