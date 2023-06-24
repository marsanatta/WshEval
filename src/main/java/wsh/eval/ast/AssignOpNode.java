package wsh.eval.ast;

import wsh.eval.token.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

/**
 * Assign operator node
 * It's an operator node that consists of the assign operator (=,+=,-=,*=,/=,%=)
 * ,a var node to be assigned and expr node represents the value to be assigned to
 */
@Getter
@Builder
public class AssignOpNode implements TreeNode {

    @NonNull
    private final VarNode left;
    @NonNull
    private final Token op;
    @NonNull
    private final TreeNode right;

    @Override
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitAssignOpNode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AssignOpNode) {
            AssignOpNode another = (AssignOpNode) obj;
            return left.equals(another.left) && op.equals(another.op) && right.equals(another.right);
        } else {
            return false;
        }
    }
}
