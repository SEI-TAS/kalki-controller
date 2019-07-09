#include <stdio.h>
#include <jni.h>
#include <string.h>
#include "edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine.h"
JNIEXPORT void JNICALL
Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_UNTSStateMachine_generateNextState(JNIEnv *env, jobject fsmObj)
{
    (*env) -> MonitorEnter(env, fsmObj);
	jclass untsClass = (*env) -> GetObjectClass(env, fsmObj);
	if(untsClass == NULL){
	    printf("class obj returned null\n");
	    return;
	}
	jfieldID untsCurrentStateField = (*env) -> GetFieldID(env, untsClass, "currentState", "I");
	jfieldID untsCurrentEventField = (*env) -> GetFieldID(env, untsClass, "currentEvent", "Ljava/lang/String;");
	if(untsCurrentStateField == NULL | untsCurrentEventField == NULL){
	    printf("class fields returned null\n");
	    return;
	}
	//get string elements for current event
	jstring eventJString = (*env) -> GetObjectField(env, fsmObj, untsCurrentEventField);
	const char *eventString = (*env)->GetStringUTFChars(env, eventJString, NULL);
	//get string elements for current state
	jint untsCurrentState = (*env)->GetIntField(env, fsmObj, untsCurrentStateField);
	if (untsCurrentState==1)
	{
		if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-temperature-avg\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magnetometer-online-low\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-login\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-gyro-secondary")==0)
		{
			printf("unts-gyro-secondary\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("brute-force event\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else
		{
			printf("incorrect alert type\n");
		}
	}
	else if (untsCurrentState==2)
	{
		if (strcmp(eventString, "unts-gyro-seconday")==0)
		{
			printf("unts-gyro-secondary\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer-online-high")==0)
		{
			printf("unts-magnetometer-online-high\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-gyro")==0)
		{
			printf("unts-gyro\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "default-login")==0)
		{
			printf("default-logint\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-acceleration")==0)
		{
			printf("unts-acceleration\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-temperature-avg")==0)
		{
			printf("unts-acceleration\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "state-reset")==0)
		{
			printf("brute-force event\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
		}
		else if (strcmp(eventString, "brute-force")==0)
		{
			printf("brute-force event\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-temperature-online")==0)
		{
			printf("unts-temperature-online\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);;
		}
		else if (strcmp(eventString, "device-unavailable")==0)
		{
			printf("device-unavailable\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer")==0)
		{
			printf("unts-magnetometer\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-temperature")==0)
		{
			printf("unts-temperature\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "unts-magnetometer-online-low")==0)
		{
			printf("unts-magentometer-online-low\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else if (strcmp(eventString, "max-login-attempts")==0)
		{
			printf("max-login-attempts\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, untsCurrentState = untsCurrentState + 1);
		}
		else
		{
			printf("incorrect alert type\n");
		}
	}
	else if (untsCurrentState==3)
	{
		if (strcmp(eventString, "state-reset")==0)
		{
			printf("brute-force event\n");
			(*env) -> SetIntField(env, fsmObj, untsCurrentStateField, 0);
		}
		else
		{
			printf("incorrect alert type\n");
		}
	}
	else
	{
		printf("incorrect state type\n");
	}
	(*env) -> ReleaseStringUTFChars(env, eventJString, eventString);
	(*env) -> MonitorExit(env, fsmObj);
	//Need to dump local values
	(*env) -> DeleteLocalRef(env, fsmObj);
}