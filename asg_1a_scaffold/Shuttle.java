import java.util.Random;

/**
 * Haichao Song
 * Description:
 */
public class Shuttle extends VaccineHandlingThread {

    protected Vial vial;
    protected Carousel carousel;
    protected InspectionBay inspectionBay;
    protected boolean occupied;

    final private static String indentation = "                  ";

    public Shuttle(Carousel carousel, InspectionBay inspectionBay) {
        super();
        this.carousel = carousel;
        this.inspectionBay = inspectionBay;
        this.occupied = false;
    }

    public void run() {
        synchronized (this) {
            while (!isInterrupted()) {
                try{
                    if(carousel.checkTag()) {
                        if (inspectionBay.getOccupied()) {
                            wait();
                        } else if (this.occupied) {
                            wait();
                        } else {
                            vial = carousel.shuttleVial();
                            inspectionBay.setVial(vial);
                            System.out.println(indentation + vial + " [ c3 -> S ]");
                            this.occupied = true;

                            inspectionBay.checkVial();
                            System.out.println(indentation + vial + " [ S -> I ]");
                            this.occupied = false;

                            vial = inspectionBay.getVial();
                            System.out.println(indentation + vial + " [ I -> S ]");

                            if (carousel.checkCompartment()) {
                                carousel.returnVial(vial);
                                System.out.println(indentation + vial + " [ S -> c3 ]");
                            } else {
                                wait();
                            }
                        }
                    }

                    notifyAll();
                    Random random = new Random();
                    int sleepTime = random.nextInt(Params.SHUTTLE_TIME);
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }
    }

}
