#include <stdio.h>
#include <string.h>
#include "../../fsm/headers/edu_cmu_sei_kalki_controller_fsm_UdooNeoStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_kalki_controller_fsm_UdooNeoStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate, jint defaultSamplingRate)
{
	char eventString[256];
	int newCurrentState;
    int newSamplingRate = samplingRate;
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));

	if (currentState==1)
    {
        newSamplingRate = samplingRate;
        if (strcmp(eventString, "brute-force")==0)
        {
            printf("brute-force event\n");
            newCurrentState = currentState +1;
        }
        else if (strcmp(eventString, "unts-abnormal-traffic")==0)
        {
            printf("unts-abnormal-traffic\n");
            newCurrentState = currentState +1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "device-unavailable")==0)
        {
            printf("device-unavailable\n");
            newCurrentState = currentState +1;
        }
        else if (strcmp(eventString, "unts-gyro")==0)
        {
            printf("unts-gyro\n");
            newCurrentState = currentState +1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "unts-acceleration")==0)
        {
            printf("unts-acceleration\n");
            newCurrentState = currentState +1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "unts-magnetometer")==0)
        {
            printf("unts-magnetometer\n");
            newCurrentState = currentState +1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "unts-temperature")==0)
        {
            printf("unts-temperature\n");
            newCurrentState = currentState +1;
            newSamplingRate = defaultSamplingRate/2;
        }
        else if (strcmp(eventString, "state-reset")==0)
        {
            printf("state-reset event\n");
            newCurrentState = 1;
            newSamplingRate = defaultSamplingRate;
        }
        else
        {
            printf("incorrect alert type\n");
            newCurrentState = currentState;
        }
    }
	else if (currentState==2)
	{
	    newSamplingRate = samplingRate;
	    if (strcmp(eventString, "brute-force")==0)
        {
            printf("brute-force event\n");
            newCurrentState = currentState;
        }
        else if (strcmp(eventString, "unts-abnormal-traffic")==0)
        {
            printf("unts-abnormal-traffic\n");
            newCurrentState = currentState +1;
        }
        else if (strcmp(eventString, "device-unavailable")==0)
        {
            printf("device-unavailable\n");
            newCurrentState = currentState +1;
        }
		else if (strcmp(eventString, "unts-gyro-avg")==0)
		{
			printf("unts-gyro-avg\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-acceleration-avg")==0)
		{
			printf("unts-acceleration-avg\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-temperature-avg\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-avg")==0)
		{
			printf("unts-magnetometer-avg\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = defaultSamplingRate;
		}
		else
		{
			printf("incorrect alert type\n");
			newCurrentState = currentState;
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
			newCurrentState = currentState;
		}
	}
	else
	{
		printf("not reset event\n");
		newCurrentState = currentState;
	}
	//Build returnArray
	int cArray[2];
    cArray[0] = newCurrentState;
    cArray[1] = newSamplingRate;
    jintArray returnArray = (*env) ->NewIntArray(env, 2);
    (*env) -> SetIntArrayRegion(env, returnArray, 0, 2, cArray);
    return (returnArray);
}
