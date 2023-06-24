package wsh.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.token.Token;
import wsh.eval.number.Number;

/**
 * Binary operator node
 * It's a operator node that consists of the binary operator (+,-,*,/,%)
 * and two expr on left and right which the operator operates
 */
@Getter
@Builder
public class BinaryOpNode implements TreeNode {

    @NonNull
    private final TreeNode left;
    @NonNull
    private final Token op;
    @NonNull
    private final TreeNode right;

    @Override
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitBinaryOpNode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOpNode) {
            BinaryOpNode another = (BinaryOpNode) obj;
            return left.equals(another.left) && op.equals(another.op) && right.equals(another.right);
        } else {
            return false;
        }
    }
}
