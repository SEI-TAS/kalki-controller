package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class PHLEStateMachine extends StateMachine {

    static {
        System.loadLibrary("phlefsm");
    }

    public static void main(String[] args) {
        PHLEStateMachine main = new PHLEStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }

    @Override
    public void run() {
        System.out.println("PHLE pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("PHLE post gen: current state: " + this.getCurrentState());
        //uncomment this for running
        Postgres.insertDeviceSecurityState(new DeviceSecurityState(this.getDeviceID(), this.getCurrentState()));
    }

    public PHLEStateMachine(String name, int id) {
        super(name, id);
    }

    private native void generateNextState();

}

