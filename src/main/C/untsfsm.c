#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine.h"
JNIEXPORT int JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine_generateNextState(JNIEnv *env, jobject fsmObj,
jstring alertType, jint currentState)
{
    (*env) -> MonitorEnter(env, fsmObj);
	char eventString[256];
	strcpy(eventString, (*env) -> GetStringUTFChars(env, alertType, NULL));
	if (currentState==0)
	{
		if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-temperature-avg\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magnetometer-online-low\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-gyro-secondary")==0)
		{
			printf("unts-gyro-secondary\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("brute-force event\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			return currentState = currentState +1;
		}
		else
		{
			printf("incorrect alert type\n");
			return currentState;
		}
	}
	else if (currentState==1)
	{
		if (strcmp(eventString, "unts-gyro-seconday")==0)
		{
			printf("unts-gyro-secondary\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-logint\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-acceleration\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("brute-force event\n");
			return currentState = 0;
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magentometer-online-low\n");
			return currentState = currentState +1;
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			return currentState = currentState +1;
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
			printf("brute-force event\n");
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
		printf("not reset event\n");
		return currentState;
	}
	(*env) -> MonitorExit(env, fsmObj);
}