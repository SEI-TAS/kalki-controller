# Kalki-Controller
 
## Prerequisites
- Make sure JAVA_HOME variable is set
- Make sure GCC is installed
- Requires Kalki-DB library
- Requires connection to database

## Usage
- gradle build for testing and library generation
- gradle run for controller startup

## Model Devlopment:

### Enterprise Architect:
-	Requirements:
-	Clone kalki-uml with the StateMachineCode project
-	Open StateMachineCode project in Enterprise Architect Ultimate Edition
-	Important Objects

#### Action
 - What is executed when control flow enters the object
   - Insert code snippet within action for event
#### Control Flow
 -	A control flow is what determines the if/else if/else structure of the code
 - A control flow with a guard becomes else if/if statement
 - An empty control flow is an else statement

#### Merge
 - Creates decision tree structure must merge control flows from actions back to a merge node to compile correctly
 
#### How to create device model
-	Copy full device structure of DeviceActivity into a new structure
-	Add Actions for each different event in Normal and Suspicious 
-	Link State to State Actions with control flows
-	Link State Actions to mergeState node

Code Snippets

##### MonitorEnter
  - JNI equivalent to Synchronized():
    - '(*env) -> MonitorEnter(env, fsmObj)'
  - fsmObj and env are defined in method header
##### retreiveObj

- retrieves the class object from java environment
  - `jclass (deviceName)Class = (*env) -> GetObjectClass(env, fsmObj);`

- Check to make sure the class object was successfully retreived
  - `if((deviceName)Class == NULL){printf("class obj returned null\n"); return;}`

- Retrieves the variable field “currentState” from the class object
- “I” means the desired type for the variable is an integer
  - `jfieldID (deviceName)CurrentStateField = (*env) -> GetFieldID(env, (deviceName)Class, "currentState", "I");`

- Retrieves the variable field “currentEvent” from the class object 
- “Ljava/lang/String” is Java reference
  - ```jfieldID (deviceName)CurrentEventField = (*env) -> GetFieldID(env, (deviceName)Class, "currentEvent", "Ljava/lang/String;");```

- Check to make sure the class fields do not return NULL values
  - `If ((deviceName)CurrentStateField == NULL | (deviceName)CurrentEventField == NULL) {printf("class obj returned null\n");return;}`

- Retrieves the JNI representation of the string object from the class object
  - ``` jstring eventJString = (*env) -> GetObjectField(env, fsmObj, (deviceName)CurrentEventField);```

- Moves the jstring object into UTF characters that are can be accessed by methods from <string.h>
  - ```const char *eventString = (*env)->GetStringUTFChars(env, eventJString, NULL);```	

- Retreives the value of the currentState from the class object and places it into a variable for use
  - ```jint (deviceName)CurrentState = (*env)->GetIntField(env, fsmObj, (deviceName)CurrentStateField)```	

##### control flow guard code

 - Compares the event string and an alert using string compare from <string.h>
 - Leave the last line of code per action or guard without a ; 
 - Leave control flow blank for else statement	
   - ```strcmp(eventString, "alert-name")==0```


##### action code

 - 	debug statement
   - ```printf("wemo-today-kwh\n");```

 - set the currentState of the retreived class object to currentState++ transitioning it to the next state
   - ```(*env) -> SetIntField(env, fsmObj, (deviceName)CurrentStateField, (deviceName)CurrentState = wemoCurrentState + 1)```	
##### monitorExit

 - Release the references to the UTF chars used for the alert checks (MUST DO)
   - ```(*env) -> ReleaseStringUTFChars(env, eventJString, eventString);```	

 - 	Exits the Monitor statement
   - ```(*env) -> MonitorExit(env, fsmObj);```

 - Need to dump local values
   - ```(*env) -> DeleteLocalRef(env, fsmObj)```

#### Generating C Header Files:
-	In terminal inside the JavaDevices directory call
  -	```Javac -h . StateMachine.java (deviceName)StateMachine.java ```
- Move the generated .h file into the C folder
-	Add include statement with name of generated .h file into the C code template file for the device

#### C File Template
•	Create empty file (deviceName)fsm.c
•	Paste Template below into file
  - `JNIEXPORT void JNICALL Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_(DeviceName)StateMachine_generateNextState(JNIEnv *env, jobject fsmObj){//generated method code goes here}`
#### Gradle Build File:
-	Copy from components replacing device name to build libraries on gradle build
-	Add pointer to file in Test and Run tasks
-	Add dependency to bottom of build file for both test and run
