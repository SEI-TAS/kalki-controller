package kalki.controller;

public class PHLE extends StateMachine{
    static {
        System.loadLibrary("fsm");
    }
    public void run() {
        this.generateNextState();
        System.out.println("finished in JNI Code");
        return;
    }
    public PHLE(String name) { super(name);}

    private native void generateNextState();

    public void printConfirmation(){
        System.out.println("completed C code in PHLE");
    }
}


