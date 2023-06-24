package wsh.eval.exceptions;

/**
 * Unknown Number type.
 * This is to indicates developer having unknown number type defined in the code logic
 */
public class UnknownNumberTypeException extends RuntimeException {
    public UnknownNumberTypeException(String message) {
        super(message);
    }
}
