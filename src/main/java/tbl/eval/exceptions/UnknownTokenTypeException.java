package tbl.eval.exceptions;

/**
 * Unknown token type
 * This is to indicates developer having unknown token type defined in the code logic
 */
public class UnknownTokenTypeException extends RuntimeException {
    public UnknownTokenTypeException(String message) {
        super(message);
    }
}
