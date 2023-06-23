package tbl.eval.exceptions;

/**
 * Exception for failing to build token
 */
public class BuildTokenException extends RuntimeException {
    public BuildTokenException(String message) {
        super(message);
    }
}
