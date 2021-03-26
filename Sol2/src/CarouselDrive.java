/**
 * A carousel drive rotates a carousel as often as possible, but only
 * when there is a vial on the carousel not in the final compartment.
 */

public class CarouselDrive extends VaccineHandlingThread {

    // the carousel to be handled
    protected Carousel carousel;

    /**
     * Create a new CarouselDrive with a carousel to rotate.
     */
    public CarouselDrive(Carousel carousel) {
        super();
        this.carousel = carousel;
    }

    /**
     * Move the carousel as often as possible, but only if there 
     * is a vial on the carousel which is not in the final compartment.
     */
    public void run() {
        while (!isInterrupted()) {
            try {
                // spend DRIVE_TIME milliseconds rotating the carousel
                Thread.sleep(Params.DRIVE_TIME);

//                System.out.println(carousel.name);
//                if (carousel.name == "C") {
//                    System.out.println(carousel.checkCompartment(2));
//                    if(carousel.checkCompartment(2)) {
//                        System.out.println(carousel.checkTag());
//                    }
//                }

                if(carousel.name == "C" && carousel.checkCompartment(2) && carousel.checkTag()) {
//                    System.out.println("Stop Rotating");
                    continue;
                } else {
                    carousel.rotate();
                }

            } catch (OverloadException e) {
                terminate(e);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }

        System.out.println("CarouselDrive terminated");
    }
}
