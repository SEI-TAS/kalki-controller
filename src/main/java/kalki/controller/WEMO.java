package kalki.controller;

public class WEMO extends StateMachine{
    static {
        System.loadLibrary("fsm");
    }
    public void run() {
        this.generateNextState();
    }
    public WEMO(String name) { super(name);}

    private native void generateNextState();

    public static void main(String[] args) {
        WEMO test = new WEMO("device");
        test.setEvent(0);
    }
}

