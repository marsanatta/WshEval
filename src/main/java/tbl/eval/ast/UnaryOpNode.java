package tbl.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.VariableNotFoundException;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Unary Operation Node
 * It's a operator node that consists of the unary operator (+, -) and a expr which the operator operates
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
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitUnaryOpNode(this);
    }
}
