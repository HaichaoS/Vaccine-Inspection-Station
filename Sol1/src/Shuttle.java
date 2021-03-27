/**
 * Haichao Song
 * Description: Movement of vials to and from the inspection bay
 * is handled by a single bi-directional shuttle
 * can also hold only one vial at a time
 */
public class Shuttle extends VaccineHandlingThread {

    protected Vial vial;    // the vial shuttle is holding
    protected Carousel carousel;   // the carousel shuttle connects to
    protected InspectionBay inspectionBay;  // the inspection bay shuttle connects to
    protected boolean occupied;    // a variable shows if the bay is already occupied by a vial

    final private static String indentation = "                  ";

    /**
     * Create a new, empty shuttle, initialised to be empty.
     * @param carousel the carousel shuttle connects to
     * @param inspectionBay the inspection bay shuttle connects to
     */
    public Shuttle(Carousel carousel, InspectionBay inspectionBay) {
        super();
        this.carousel = carousel;
        this.inspectionBay = inspectionBay;
        this.occupied = false;
    }

    /**
     * the main method send vials between carousel and inspection bay
     */
    public void run() {
        synchronized (this) {
            while (!isInterrupted()) {
                try{

                    if(carousel.checkTag()) {

                        while(inspectionBay.getOccupied() || this.occupied) {
                            wait();
                        }

                        // get the vial from carousel to shuttle
                        vial = carousel.shuttleVial();
                        System.out.println(indentation + vial + " [ c3 -> S ]");
                        this.occupied = true;

                        // send the vial from shuttle to the inspection bay
                        sleep(Params.SHUTTLE_TIME);
                        System.out.println(indentation + vial + " [ S -> I ]");
                        this.occupied = false;
                        inspectionBay.setVial(vial);

                    } else if (inspectionBay.checkInspected()) {

                        // get the vial from inspection bay to shuttle after inspection
                        vial = inspectionBay.getVial();
                        this.occupied = true;
                        System.out.println(indentation + vial + " [ I -> S ]");

                        while(!carousel.checkCompartment()) {
                            wait();
                        }

                        // send the vial from shuttle to  carousel
                        sleep(Params.SHUTTLE_TIME);
                        carousel.returnVial(vial);
                        System.out.println(indentation + vial + " [ S -> c3 ]");
                        this.occupied = false;

                    }

                    notifyAll();

                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }
    }

}
