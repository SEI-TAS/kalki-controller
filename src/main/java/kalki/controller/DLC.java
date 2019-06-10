package kalki.controller;

public class DLC extends StateMachine{
    static {
        System.loadLibrary("fsm");
    }
    public void run() {
        this.generateNextState();
    }
    public DLC(String name) { super(name);}

    private native void generateNextState();

    public static void main(String[] args) {
        DLC test = new DLC("device");
        test.setEvent(0);
    }
}
