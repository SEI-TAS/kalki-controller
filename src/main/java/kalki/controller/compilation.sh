#!/bin/bash
echo Exporting JAVA_HOME
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-12.0.1.jdk/Contents/Home/
echo $JAVA_HOME
echo Compiling C Library...
gcc -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/darwin" -dynamiclib -o libfsm.dylib FSM.c
echo Compiling Java classes...
Javac -h . PHLE.java UNTS.java DLC.Java DeviceManager.java StateMachine.java WEMO.java
echo Compiling Controller code...
Javac -h . IOTController.java
