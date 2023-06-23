package tbl.eval.exceptions;

/**
 * Unknown token type
 */
public class UnknownTokenTypeException extends RuntimeException {
    public UnknownTokenTypeException(String message) {
        super(message);
    }
}
