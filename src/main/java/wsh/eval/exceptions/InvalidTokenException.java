package wsh.eval.exceptions;

/**
 * Exception for invalid token
 * This is to indicates user input invalid token in the expression
 */
public class InvalidTokenException extends Exception {

    public InvalidTokenException(String message) {
        super(message);
    }
}
