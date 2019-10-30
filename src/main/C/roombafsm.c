#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_ROOMBAStateMachine.h"

JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_ROOMBAStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
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
        else if (strcmp(eventString, "roomba-unexpected-command")==0)
        {
            printf("roomba-unexpected-command\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "roomba-cloud-traffic")==0)
        {
            printf("roomba-cloud-traffic\n");
            newCurrentState = currentState + 1;
        }
        else if (strcmp(eventString, "roomba-unexpected-auth")==0)
        {
            printf("roomba-unexpected-auth\n");
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
		if (strcmp(eventString, "roomba-auth-attempts")==0)
		{
			printf("roomba-auth-attempts\n");
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
