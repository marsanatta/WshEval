package tbl.eval.exceptions;

/**
 * Exception for variable not found
 */
public class VariableNotFoundException extends Exception {
    public VariableNotFoundException(String message) {
        super(message);
    }
}
