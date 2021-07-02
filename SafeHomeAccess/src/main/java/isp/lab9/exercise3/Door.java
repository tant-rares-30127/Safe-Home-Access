package isp.lab9.exercise3;


public class Door {
    private DoorStatus status;

    public Door() {
        this.status=DoorStatus.CLOSE;
    }
    
    public void lockDoor(){
        this.status=DoorStatus.CLOSE;
    }
    
    public void unlockDoor(){
        this.status=DoorStatus.OPEN;
    }

    public DoorStatus getStatus() {
        return status;
    }

}