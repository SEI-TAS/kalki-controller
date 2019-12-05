# Kalki-Controller
 
## Prerequisites
- Make sure GCC is installed
- Requires Kalki-DB library
- Requires connection to database

## Usage
- Execute `./gradlew build` for library generation
- To run, first execute build command above, the `./gradlew run` for controller startup

## Model Development in EA:

### Enterprise Architect:
-	Requirements:
-	Clone kalki-uml with the StateMachineCode project
-	Open StateMachineCode project in Enterprise Architect Ultimate Edition

### Important EA Models
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

### Code Snippets

#### Create C String

- Create c style string for use in Control Flow
  ```C
  char eventString[256];
  ```
- Create int for sampling rate and currentState
``` C
int newCurrentState
int newSamplingRate
```
##### Control Flow guard code

 - Compares the event string and an alert using string compare from <string.h>
 - Leave the last line of code per action or guard without a ; 
 - Leave control flow blank for else statement	
   - For checking current state: 
   ```C
   currentState == 1 2 or 3
   ```
   - For checking alert type: 
   ```C
   strcmp(eventString, "alert-name")==0
   ```

##### Action code

 - debug statement
 ```C
 printf("event-name\n");
 ```
 - set return current state++ for state transition, set to 1 for reset, return currentstate for still in attack
   - Alert (non reset)
    ```C
    newCurrentState = currentState + 1
    ```	
    - if transition from state normal to suspicious: 
    ```C
    newSamplingRate = samplingRate*2
    ```
    - else 
    ``` C
    newSamplingRate = samplingRate
    ```
   - Alert (reset)
   ```C
   newCurrentState = 1; 
   ```
   ```C
   newSamplingRate = samplingRate/2;
   ```

##### Ending Action
```C
int cArray[2];
cArray[0] = newCurrentState;
cArray[1] = newSamplingRate;
jintArray returnArray = (*env) ->NewIntArray(env, 2);
(*env) -> SetIntArrayRegion(env, returnArray, 0, 2, cArray);
return (returnArray); 
```

#### Generating C Header Files:
-	In terminal inside the JavaDevices directory call
  -	```javac -h . StateMachine.java (deviceName)StateMachine.java ```
- Move the generated .h file into the C folder
- Make sure stdio.h and string.h are included
-	Add include statement with name of generated .h file into the C code template file for the device

## Adding a New FSM to the Project

1. Add a new component to build.gradle.
   1. Inside `model { components {`, add a new line for the new component, with the format `<name>fsm(NativeLibrarySpec)`. I.e., `roombafsm(NativeLibrarySpec)`
1. Create the source folders.
   1. Create a folder  called `<name>fsm` (using the same name as the component, i.e., `rooombafsm`) inside the `src` folder. 
   1. Create a subfolder inside it called `c`
1. Put the C code inside the `c` subfolder.

#### C File Template
- Create empty file (deviceName)fsm.c
- Paste Template below into file
```Java
JNIEXPORT void JNICALL Java_edu_cmu_sei_ttg_kalki_controller_JavaDevices_(DeviceName)StateMachine_generateNextState(JNIEnv *env, jobject fsmObj){//generated method code goes here}
```
