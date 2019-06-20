#!/bin/bash
echo Exporting JAVA_HOME
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/
echo $JAVA_HOME
echo Compiling C Libraries...
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -dynamiclib -o libdlcfsm.dylib DLC_State_Machine.c
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -dynamiclib -o libphlefsm.dylib PHLE_State_Machine.c
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -dynamiclib -o libuntsfsm.dylib UNTS_State_Machine.c
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -dynamiclib -o libwemofsm.dylib WEMO_State_Machine.c
javac -h . WEMOStateMachine.java DLCStateMachine.java PHLEStateMachine.java UNTSStateMachine.java StateMachine.java
echo Everything looks good!

