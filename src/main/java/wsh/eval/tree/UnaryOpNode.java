package wsh.eval.tree;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.token.Token;
import wsh.eval.number.Number;

/**
 * Unary Operation Node
 * It's a operator node that consists of the unary operator (+, -) and a expr which the operator operates
 */
@Getter
@Builder
public class UnaryOpNode implements TreeNode {
    @NonNull
    private final Token op;
    @NonNull
    private final TreeNode right;

    @Override
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitUnaryOpNode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryOpNode) {
            UnaryOpNode another = (UnaryOpNode) obj;
            return op.equals(another.op) && right.equals(another.right);
        } else {
            return false;
        }
    }

}
