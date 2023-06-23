package tbl.eval.exceptions;

/**
 * Exception for invalid token in the expression
 */
public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }
}
