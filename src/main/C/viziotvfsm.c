#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_VIZIOTVStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_VIZIOStateMachineStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate, jint defaultSamplingRate)
{
	char eventString[256];
	int newCurrentState;
	int newSamplingRate;
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));;
    if (currentState==1)
    {
        newSamplingRate = samplingRate;
        if (strcmp(eventString, "state-reset")==0)
        {
            printf("state-reset event\n");
            newCurrentState = 1;
        }
        else if (strcmp(eventString, "vizio-connected-devices")==0)
        {
            printf("vizio-connected-devices\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "vizio-input-source")==0)
        {
            printf("vizio-input-source\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "vizio-unexpected-auth")==0)
        {
            printf("vizio-unexpected-auth\n");
            newCurrentState = currentState +1;
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
		if (strcmp(eventString, "vizio-combination-alert")==0)
		{
			printf("vizio-combination-alert\n");
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
