package tbl.eval.exceptions;

/**
 * Exception for failing to build token
 * This is to indicates developer build Token that violates the rule (See Token.Builder for the validation)
 */
public class BuildTokenException extends RuntimeException {
    public BuildTokenException(String message) {
        super(message);
    }
}
