#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine.h"
JNIEXPORT jintArray JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState, jint samplingRate)
{
	char eventString[256];
	int newCurrentState;
    int newSamplingRate = samplingRate;
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));
	if (currentState==2)
	{
	    newSamplingRate = samplingRate;
		if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-temperature-avg\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magnetometer-online-low\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-gyro-secondary")==0)
		{
			printf("unts-gyro-secondary\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate/2;
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			newCurrentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-abnormal-traffic")==0)
		{
			printf("unts-abnormal-traffic\n");
			newCurrentState = currentState +1;
		}
		else
		{
			printf("incorrect alert type\n");
			newCurrentState = currentState;
		}
	}
	else if (currentState==1)
	{
		if (strcmp(eventString, "unts-gyro-seconday")==0)
		{
			printf("unts-gyro-secondary\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-logint\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-acceleration\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("state-reset event\n");
			newCurrentState = 1;
			newSamplingRate = samplingRate;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magentometer-online-low\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			newCurrentState = currentState +1;
			newSamplingRate = samplingRate*2;
		}
		else if (strcmp(eventString, "unts-abnormal-traffic")==0)
		{
			printf("unts-abnormal-traffic\n");
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