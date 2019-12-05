#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_kalki_controller_fsm_DLCStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_kalki_controller_fsm_DLCStateMachine_generateNextState(JNIEnv *env, jobject fsmObj, jstring alertType, jint currentState, jint samplingRate, jint defaultSamplingRate)
{
    int newCurrentState;
    int newSamplingRate;
    char eventString[256];
    strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));

	if (currentState==1)
    {
        newSamplingRate = samplingRate;
        if (strcmp(eventString, "max-login-attempts")==0)
        {
            printf("max-login-attempts\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "state-reset")==0)
        {
            printf("state-reset event\n");
            newCurrentState = 1;
            newSamplingRate = defaultSamplingRate;
        }
        else if (strcmp(eventString, "device-unavailable")==0)
        {
            printf("device-unavailable\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "dlc-motion-sense")==0)
        {
            printf("dlc-motion-sense\n");
            newCurrentState = 1;
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
		if (strcmp(eventString, "dlc-motion-sense")==0)
		{
			printf("dlc-motion-sense\n");
			newCurrentState = 2;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = defaultSamplingRate;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = 2;
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
			newSamplingRate = samplingRate;
		}
	}
	else
	{
		printf("incorrect state type\n");
		newCurrentState = currentState;
	    newSamplingRate = samplingRate;

	}
	//Build returnArray
    int cArray[2];
    cArray[0] = newCurrentState;
    cArray[1] = newSamplingRate;
    jintArray returnArray = (*env) ->NewIntArray(env, 2);

    (*env) -> SetIntArrayRegion(env, returnArray, 0, 2, cArray);
    return (returnArray);
}
