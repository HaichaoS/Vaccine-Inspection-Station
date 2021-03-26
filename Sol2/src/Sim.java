/**
 * The main class for the vaccine fill/finish system simulator.
 */

public class Sim {
    /**
     * The main method to run the simulator.
     */
    public static void main(String[] args) {
        
    	// Create system components
        Carousel carousel = new Carousel(Params.CAROUSEL_NAME, 5);
        Carousel addCarousel = new Carousel(Params.CAROUSEL_ADD_NAME, 2);
        Producer producer = new Producer(carousel);
        Consumer consumer = new Consumer(carousel);
        Consumer addConsumer = new Consumer(addCarousel);
        CarouselDrive driver = new CarouselDrive(carousel);
        CarouselDrive addDriver = new CarouselDrive(addCarousel);
        InspectionBay inspectionBay = new InspectionBay();
        Shuttle shuttle = new Shuttle(carousel, inspectionBay, Params.SHUTTLE_NAME);
        Shuttle addShuttle = new Shuttle(addCarousel, inspectionBay, Params.SHUTTLE_ADD_NAME);

        // start threads
        consumer.start();
        addConsumer.start();
        producer.start();
        driver.start();
        addDriver.start();
        inspectionBay.start();
        shuttle.start();
        addShuttle.start();

        // check all threads still live
        while (consumer.isAlive() && addConsumer.isAlive() &&
               producer.isAlive() && 
               driver.isAlive() && addDriver.isAlive() &&
                inspectionBay.isAlive() &&
                shuttle.isAlive() && addShuttle.isAlive()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                VaccineHandlingThread.terminate(e);
            }
        }

        // interrupt other threads
        consumer.interrupt();
        addConsumer.interrupt();
        producer.interrupt();
        driver.interrupt();
        addDriver.interrupt();
        inspectionBay.interrupt();
        shuttle.interrupt();
        addShuttle.interrupt();

        System.out.println("Sim terminating");
        System.out.println(VaccineHandlingThread.getTerminateException());
        System.exit(0);
    }
}
