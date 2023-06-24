package wsh.eval.exceptions;

/**
 * Exception for failing to build variable node
 * This is to indicates developer build VarNode that violates the rule (See VarNode.Builder for the validation)
 */
public class BuildVarNodeException extends RuntimeException {
    public BuildVarNodeException(String message) {
        super(message);
    }
}
