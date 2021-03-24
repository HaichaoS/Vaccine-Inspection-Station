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
//                        System.out.println("Scanner checked Tag!!");
                        if (inspectionBay.getOccupied()) {
                            wait();
                        } else if (this.occupied) {
                            wait();
                        } else {
                            vial = carousel.shuttleVial();
                            inspectionBay.setVial(vial);
                            System.out.println(indentation + vial + " [ c3 -> S ]");
                            this.occupied = true;

                            sleep(Params.SHUTTLE_TIME);

                            System.out.println(indentation + vial + " [ S -> I ]");
                            inspectionBay.checkVial();
                            this.occupied = false;

                            sleep(Params.INSPECT_TIME);

                            vial = inspectionBay.getVial();
                            this.occupied = true;
                            System.out.println(indentation + vial + " [ I -> S ]");
                            
                            sleep(Params.SHUTTLE_TIME);

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
