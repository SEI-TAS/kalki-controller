#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_PHLEStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_PHLEStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate)
{
	char eventString[256];
	int newCurrentState;
	int newSamplingRate;
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));;
	if (currentState==2)
	{
	    newSamplingRate = samplingRate;
		if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "phle-odd-one-out")==0)
		{
			printf("phle-odd-one-out\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "phle-time-off")==0)
		{
			printf("phle-time-off\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate/2;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			newCurrentState = currentState +1;
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
			newSamplingRate = samplingRate/2;
		}
		else
		{
			printf("not reset event\n");
			newCurrentState = currentState;
			newSamplingRate = samplingRate;
		}
	}
	else if (currentState==1)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;

		}
		else if (strcmp(eventString, "phle-time-off")==0)
		{
			printf("phle-time-ff\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login event\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "phle-odd-one-out")==0)
		{
			printf("phle-odd-one-out\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else
		{
			printf("incorrect alert type\n");
			newCurrentState = currentState;
			newSamplingRate = samplingRate;
		}
	}
	else
	{
		printf("incorrect state type\n");
		newCurrentState = currentState;
		newSamplingRate = samplingRate;
	}
	//Build return array
	int cArray[2];
    cArray[0] = newCurrentState;
    cArray[1] = newSamplingRate;
    jintArray returnArray = (*env) ->NewIntArray(env, 2);
    (*env) -> SetIntArrayRegion(env, returnArray, 0, 2, cArray);
    return (returnArray);
}