package tbl.eval.exceptions;

/**
 * Exception for failing to build number node
 * This is to indicates developer build NumberNode that violates the rule (See NumberNode.Builder for the validation)
 */
public class BuildNumberNodeException extends RuntimeException {
    public BuildNumberNodeException(String message) {
        super(message);
    }

}
