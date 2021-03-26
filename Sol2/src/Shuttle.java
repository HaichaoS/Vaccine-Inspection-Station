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
    protected String name;

    final private static String indentation = "                  ";

    /**
     * Create a new, empty shuttle, initialised to be empty.
     * @param carousel the carousel shuttle connects to
     * @param inspectionBay the inspection bay shuttle connects to
     */
    public Shuttle(Carousel carousel, InspectionBay inspectionBay, String name) {
        super();
        this.carousel = carousel;
        this.inspectionBay = inspectionBay;
        this.occupied = false;
        this.name = name;
    }

    /**
     * the main method send vials between carousel and inspection bay
     */
    public void run() {
        synchronized (this) {
            while (!isInterrupted()) {
                try{

                    if(carousel.name == Params.CAROUSEL_NAME && carousel.checkTag() && (!this.occupied)) {

                        // get the vial from carousel to shuttle
                        vial = carousel.shuttleVial();
                        System.out.println(indentation + vial + " [ " + carousel.name + "3 -> " + this.name + " ]");
                        this.occupied = true;

                    } else if (carousel.name == Params.CAROUSEL_NAME && this.occupied) {

                        if (!inspectionBay.getOccupied())  {
                            // send the vial from shuttle to the inspection bay
                            sleep(Params.SHUTTLE_TIME);
                            System.out.println(indentation + vial + " [ " + this.name + " -> I ]");
                            this.occupied = false;
                            inspectionBay.setVial(vial);
//                            System.out.println(indentation + vial + " set to inspection");
                        }

                    } else if (carousel.name == Params.CAROUSEL_ADD_NAME && inspectionBay.checkInspected()) {

                        // get the vial from inspection bay to shuttle after inspection
                        vial = inspectionBay.getVial();
//                        System.out.println(indentation + vial + " get from inspection");
                        System.out.println(indentation + vial + " [ I -> " + this.name + " ]");

                        // send the vial from shuttle to  carousel
                        if (!carousel.checkCompartment(0))  {
                            sleep(Params.SHUTTLE_ADD_TIME);
                            System.out.println(indentation + vial + " [ " + this.name + " -> " + carousel.name + "1 ]");
                            carousel.putVial(vial);
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
