
/**
 * Parameters that influence the behaviour of the system.
 */

public class Params {

    // the number of compartments in this carousel
    public final static int CAROUSEL_SIZE = 5;

	// // the number of compartments in additional carousel
    public final static int CAROUSEL_ADD_SIZE = 2;
    
    // the maximum amount of time the producer waits
	public final static int PRODUCER_MAX_SLEEP = 3000;
	
	// the minimum amount of time the consumer waits
	public final static int CONSUMER_MIN_SLEEP = 500;
	
    // the maximum amount of time the consumer waits
	public final static int CONSUMER_MAX_SLEEP = 2800;
	
	// the amount of time it takes to move the belt
	public final static int DRIVE_TIME = 900;
	
	// the amount of time it takes the shuttle to move 
	// between the carousel and the inspection bay
	public final static int SHUTTLE_TIME = 900;
	
	// the amount of time it takes to inspect a vial
	public final static int INSPECT_TIME = 5000;
	
	// probability that a vial is defective
	public final static double DEFECT_PROB = 0.3;

	// the index place where shuttle connects the carousel
	public final static int SHUTTLE_INDEX = 2;

	// the amount of time it takes the shuttle to move
	// between the inspection bay and the additional shuttle
	public final static int SHUTTLE_ADD_TIME = 900;

	// the letter for long carousel
	public final static String CAROUSEL_NAME = "C";

	// the letter for additional short carousel
	public final static String CAROUSEL_ADD_NAME = "c";

	// the letter for original shuttle
	public final static String SHUTTLE_NAME = "S";

	// the letter for additional shuttle
	public final static String SHUTTLE_ADD_NAME = "s";
}
