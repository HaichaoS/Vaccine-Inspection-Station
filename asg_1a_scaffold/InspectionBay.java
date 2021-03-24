/**
 * Haichao Song
 * Description:
 */
public class InspectionBay extends VaccineHandlingThread {

    protected Carousel carousel;
    protected boolean occupied;
    protected Vial vial;

    public InspectionBay() {
        super();
        this.occupied = false;
        this.vial = null;
    }

    public void run() {
        synchronized (this) {
            while(!isInterrupted()) {
                try{
                    while(vial == null) {
                        wait();
                    }
                    checkVial();
                    notifyAll();
                } catch (InterruptedException e) {
                    this.interrupted();
                }
            }
        }
    }

    public void checkVial() {
        if(vial.isDefective()){
            vial.setInspected();
            vial.setTagged();
        } else {
            vial.setInspected();
        }
    }

    public void setVial(Vial vail) {
        this.occupied = true;
        this.vial = vail;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public Vial getVial() {
        Vial taggedVail = this.vial;
        this.vial = null;
        this.occupied = false;
        return taggedVail;
    }

}
