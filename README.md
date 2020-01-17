# Kalki-Controller
 
## Prerequisites
- Java JDK 1.8+
- GCC installed
- Kalki-DB library installed in local Maven repo (see https://github.com/SEI-TAS/kalki-db)
- PostgreSQL has to be running to run the Controller (see https://github.com/SEI-TAS/kalki-db)

## Usage
To build the controller:
1. Execute `bash full_build.sh`

To run:
1. Execute build commands above.
1. Execute `./gradlew run -x test`

## TODO
- Automatically generate <>STateMachine java files from template.
- Automatically generate C files from template.
- NOTE: Running specific tests in IntelliJ won't work since C lib path is set only in gradle 'test' task.

## Adding a New FSM to the Project

1. Add a new component to build.gradle.
   1. Inside `model { components {`, add a new line for the new component, with the format `<component>fsm(NativeLibrarySpec)`. I.e., `roombafsm(NativeLibrarySpec)`
   1. Add the following next to it, changing <component> for the component name: `{ sources.c.source.include '**/<component>fsm.c'}`
1. Create the Java class for the fsm.
   1. Copy `/src/fsm/templates/TemplateStateMachine.java` into the `fsm` Java package, renaming it to have the device type name from the DB in its name, without spaces.
      - I.e., if the device type name in the DB is "Roomba Cleaner", the Java class name should be `RoombaCleanerStateMachine`.
   1. Modify this new class code for its name and constructor to match the file name.
   1. In the static block that loads the C code, indicate the component name defined in `build.gradle` above (i.e., "roombafsm").
1. Create the C class for the fsm.
   1. Run the build command at least once for the JNI headers to be generated.
   1. Copy `/src/fsm/templates/templatefsm.c` into the `/src/fsm/c` and rename it to `<component>fsm.c`
   1. Change the name of the header file being included to the proper new header file from `/src/fsm/headers`
   1. Copy the function name definition from the corresponding header file in `/src/fsm/headers` to the C file created in the step below.
   1. Fill in the FSM code.
   
## Model Development in Enterprise Architect

### Requirements
- Enterprise Architect Ultimate Edition

### Usage
-	Open StateMachineCode.eapx project in Enterprise Architect and modify as needed.
-   Generate C code from model (details: TO-DO)

### EA Model Concepts
#### Action
 - What is executed when control flow enters the object
   - Insert code snippet within action for event
   
#### Control Flow
 - A control flow is what determines the if/else if/else structure of the code
 - A control flow with a guard becomes else if/if statement
 - An empty control flow is an else statement

#### Merge
 - Creates decision tree structure must merge control flows from actions back to a merge node to compile correctly
 
### Creating a  Device Model
-	Copy full device structure of DeviceActivity into a new structure
-	Add Actions for each different event in Normal and Suspicious 
-	Link State to State Actions with control flows
-	Link State Actions to mergeState node

## Code Snippets for new FSMs

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
