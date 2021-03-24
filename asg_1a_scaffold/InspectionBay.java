/**
 * Haichao Song
 * Description: a single inspection bay which can hold only one vial at a time.
 * If the visual inspection determines that a vial is defective, it will be tagged for destruction.
 */
public class InspectionBay extends VaccineHandlingThread {

    protected boolean occupied;    // a variable shows if the bay is already occupied by a vial
    protected Vial vial;          // the vial currently being inspected

    /**
     * Create a new, empty inspection bay, initialised to be empty.
     */
    public InspectionBay() {
        super();
        this.occupied = false;
        this.vial = null;
    }

    /**
     * check if the vial is defective and tag the vial
     */
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

    /**
     * set vial as inspected and
     * if it is needed to be tagged to "destruction
     */
    public void checkVial() {
        if(vial.isDefective()){
            vial.setInspected();
            vial.setTagged();
        } else {
            vial.setInspected();
        }
    }

    /**
     * set the vial at inspection bay and the bay as occupied
     * @param vail the vail sent to inspection bay
     */
    public void setVial(Vial vail) {
        this.occupied = true;
        this.vial = vail;
    }

    /**
     * check if the bay is occupied
     * @return true if it is occupied, otherwise false
     */
    public boolean getOccupied() {
        return this.occupied;
    }

    /**
     * get the vial from inpection bay out and set the bay to unoccupied
     * @return the vial out
     */
    public Vial getVial() {
        Vial taggedVial = this.vial;
        this.vial = null;
        this.occupied = false;
        return taggedVial;
    }

}
