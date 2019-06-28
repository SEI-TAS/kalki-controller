package edu.cmu.sei.ttg.kalki.controller.JavaDevices;

import edu.cmu.sei.ttg.kalki.database.Postgres;
import edu.cmu.sei.ttg.kalki.models.DeviceSecurityState;

public class DLCStateMachine extends StateMachine {

    static {
        System.loadLibrary("dlcfsm");
    }

    public static void main(String[] args) {

        DLCStateMachine main = new DLCStateMachine("device00", 0);
        main.setEvent("brute-force");
        new Thread(main).start();
    }
    @Override
    public void run() {
        System.out.println("DLC pre gen: current state: " + this.getCurrentState());
        this.generateNextState();
        System.out.println("DLC post gen: current state: " + this.getCurrentState());
        //Uncomment this for running
        Postgres.insertDeviceSecurityState(new DeviceSecurityState(this.getDeviceID(), this.getCurrentState()));
    }
    public DLCStateMachine(String name, int id) { super(name, id);}

    private native void generateNextState();

}
