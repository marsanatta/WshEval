package wsh.eval.exceptions;

/**
 * Exception for variable not found.
 * This is to indicate user input a non-exist variable in the expression
 */
public class VariableNotFoundException extends Exception {
    public VariableNotFoundException(String message) {
        super(message);
    }
}
