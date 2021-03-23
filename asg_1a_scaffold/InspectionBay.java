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

}
