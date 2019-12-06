#include <stdio.h>
#include <string.h>
#include "../../fsm/headers/edu_cmu_sei_kalki_controller_fsm_<NAME>StateMachine.h"

// COPY FUNCTION NAME FROM HEADER FILE HERE
{
    int newCurrentState;
    int newSamplingRate;
    char eventString[256];
    strcpy(eventString, (*env)->GetStringUTFChars(env, alertType, NULL));

	if (currentState==1)
    {
        // TODO: actions for state 1.
    }
    else if (currentState==2)
	{
        // TODO: actions for state 2.
	}
    else if (currentState==3)
	{
        // TODO: actions for state 3.
	}
	else
	{
		printf("Unknown state\n");
		newCurrentState = currentState;
	    newSamplingRate = samplingRate;
	}

	// Build returnArray
    int cArray[2];
    cArray[0] = newCurrentState;
    cArray[1] = newSamplingRate;
    jintArray returnArray = (*env)->NewIntArray(env, 2);
    (*env)->SetIntArrayRegion(env, returnArray, 0, 2, cArray);

    return (returnArray);
}
