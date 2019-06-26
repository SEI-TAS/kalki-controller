package edu.cmu.sei.ttg.kalki.controller;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class IOTControllerRun {

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(IOTControllerTest.class);
        for(Failure failure : result.getFailures()){
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());

    }
}
