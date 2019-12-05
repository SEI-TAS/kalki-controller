# Kalki-Controller
 
## Prerequisites
- Make sure GCC is installed
- Requires Kalki-DB library
- Requires connection to database

## Usage
- Execute `./gradlew build -x test` for library generation
- To run, first execute build command above, the `./gradlew run -x test` for controller startup

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

## Adding a New FSM to the Project

1. Add a new component to build.gradle.
   1. Inside `model { components {`, add a new line for the new component, with the format `<name>fsm(NativeLibrarySpec)`. I.e., `roombafsm(NativeLibrarySpec)`
1. Create the source folders.
   1. Create a folder  called `<name>fsm` (using the same name as the component, i.e., `rooombafsm`) inside the `src` folder. 
   1. Create a subfolder inside it called `c`
1. Create the Java class for the fsm.
   1. Copy `/src/fsm/templates/TemplateStateMachine.c` into the `fsm` Java package, renaming it to a proper name, i.e. `RoombaStateMachine.c`.
   1. Modify this new class code for its name and constructor to match the file name.
   1. In the static block that loads the C code, indicate the component name defined in `build.gradle` above (i.e., "roombafsm").
1. Create the C class for the fsm.
   1. Run the build command at least once for the JNI headers to be generated.
   1. Copy `/src/fsm/templates/templatefsm.c` into the `c` subfolder that was generated, and rename it to `<name>fsm.c`
   1. Change the name of the header file being included to the proper new header file from `/src/fsm/headers`
   1. Copy the function name definition from the corresponding header file in `/src/fsm/headers` to the C file created in the step below.
   1. Fill in the FSM code.
