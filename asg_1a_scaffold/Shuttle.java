/**
 * Haichao Song
 * Description:
 */
public class Shuttle extends VaccineHandlingThread {

    protected Vial vial;
    protected Carousel carousel;
    protected InspectionBay inspectionBay;

    public Shuttle(Carousel carousel, InspectionBay inspectionBay) {
        super();
        this.carousel = carousel;
        this.inspectionBay = inspectionBay;
    }

    public void run() {
        synchronized (this) {
            while (!isInterrupted()) {
                try{
                    if(carousel.checkTag()) {

                    }
                } catch (InterruptedException e) {
                    this.interrupt();
                }
            }
        }
    }

}
