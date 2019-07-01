package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class UNTSStateMachine extends StateMachine {

    static {
        System.loadLibrary("untsfsm");
    }

    public static void main(String[] args) {

        UNTSStateMachine main = new UNTSStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }

    @Override
    public void run() {
        System.out.println("UNTS pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("UNTS post gen: current state: " + this.getCurrentState());
        //uncomment this for running
        Postgres.insertDeviceSecurityState(new DeviceSecurityState(this.getDeviceID(), this.getCurrentState()));
    }

    public UNTSStateMachine(String name, int id) {
        super(name, id);
    }

    private native void generateNextState();

}