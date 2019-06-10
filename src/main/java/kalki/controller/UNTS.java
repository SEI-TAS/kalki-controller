package kalki.controller;
public class UNTS extends StateMachine{
    static {
        System.loadLibrary("fsm");
    }
    public void run() {
        this.generateNextState();
    }
    public UNTS(String name) { super(name);}

    private native void generateNextState();

    public static void main(String[] args) {
        UNTS test = new UNTS("device");
        test.setEvent(0);
    }
}