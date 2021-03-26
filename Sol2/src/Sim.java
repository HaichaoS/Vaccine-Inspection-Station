/**
 * The main class for the vaccine fill/finish system simulator.
 */

public class Sim {
    /**
     * The main method to run the simulator.
     */
    public static void main(String[] args) {
        
    	// Create system components
        Carousel carousel = new Carousel("C", 5);
        Carousel addCarousel = new Carousel("c", 2);
        Producer producer = new Producer(carousel);
        Consumer consumer = new Consumer(carousel);
        Consumer addConsumer = new Consumer(addCarousel);
        CarouselDrive driver = new CarouselDrive(carousel);
        CarouselDrive addDriver = new CarouselDrive(addCarousel);
        InspectionBay inspectionBay = new InspectionBay();
        Shuttle shuttle = new Shuttle(carousel, inspectionBay);

        // start threads
        consumer.start();
        addConsumer.start();
        producer.start();
        driver.start();
        addDriver.start();
        inspectionBay.start();
        shuttle.start();

        // check all threads still live
        while (consumer.isAlive() && addConsumer.isAlive() &&
               producer.isAlive() && 
               driver.isAlive() && addDriver.isAlive() &&
                inspectionBay.isAlive() &&
                shuttle.isAlive()) {
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

        System.out.println("Sim terminating");
        System.out.println(VaccineHandlingThread.getTerminateException());
        System.exit(0);
    }
}
