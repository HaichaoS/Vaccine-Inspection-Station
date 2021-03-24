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
                        if (inspectionBay.getOccupied() || this.occupied) {
                            wait();
                        } else {

                            // get the vial from carousel to shuttle
                            vial = carousel.shuttleVial();
                            inspectionBay.setVial(vial);
                            System.out.println(indentation + vial + " [ c3 -> S ]");
                            this.occupied = true;
                            sleep(Params.SHUTTLE_TIME);

                            // send the vial from shuttle to the inspection bay
                            System.out.println(indentation + vial + " [ S -> I ]");
                            inspectionBay.checkVial();
                            this.occupied = false;
                            sleep(Params.INSPECT_TIME);

                            // get the vial from inspection bay to shuttle after inspection
                            vial = inspectionBay.getVial();
                            this.occupied = true;
                            System.out.println(indentation + vial + " [ I -> S ]");
                            sleep(Params.SHUTTLE_TIME);

                            // send the vial from shuttle to  carousel
                            if (carousel.checkCompartment()) {
                                carousel.returnVial(vial);
                                System.out.println(indentation + vial + " [ S -> c3 ]");
                                this.occupied = false;
                            } else {
                                wait();
                            }
                        }
                    }
                    notifyAll();

                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }
    }

}
