/**
 * Haichao Song
 * Description:
 */
public class Scanner {

    public Scanner() {}

    public boolean alarm(Vial[] compartment) {

        if(compartment[2] == null) {
            return false;
        }

        if((!compartment[2].isInspected()) && compartment[2].isDefective()) {
            return true;
        } else {
            return false;
        }
    }

}
