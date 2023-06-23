package tbl.eval.exceptions;

/**
 * Exception for invalid syntax in the expression
 */
public class InvalidSyntaxException extends Exception {
    public InvalidSyntaxException(String message) {
        super(message);
    }

}
