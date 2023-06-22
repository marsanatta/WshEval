package tbl.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Unary Operation Node (+, -, ++, --)
 */
@Getter
@Builder
@NonNull
public class UnaryOpNode implements TreeNode {
    @NonNull
    private final Token op;
    @NonNull
    private final TreeNode expr;

    @Override
    public Number accept(TreeVisitor visitor) throws UnknownVariableException {
        return visitor.visitUnaryOpNode(this);
    }
}
