/**
 * Haichao Song
 * Description: A scanner mounted on the carousel in compartment three performs a rapid assessment of
 * vials as they pass, sounding an alarm when it detects a defective vial that requires a more detailed visual
 * inspection.
 * The scanner has high sensitivity (ie, it will always detect defective vials) and high specificity
 * (ie, it will only sound the alarm for defective vials).
 */
public class Scanner {

    /**
     * create a new scanner
     */
    public Scanner() {}

    /**
     * the main function of the scanner, alarm when a defective vial passes through
     * @param compartment all vials on the carousel
     * @return true if a defective vial passes scanner, otherwise false
     */
    public boolean alarm(Vial[] compartment) {

        if(compartment[Params.SHUTTLE_INDEX] == null) {
            return false;
        }

        if((!compartment[Params.SHUTTLE_INDEX].isInspected()) &&
                compartment[Params.SHUTTLE_INDEX].isDefective()) {
            return true;
        } else {
            return false;
        }
    }

}
