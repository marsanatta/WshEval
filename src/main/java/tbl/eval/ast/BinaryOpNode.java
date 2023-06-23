package tbl.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.VariableNotFoundException;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

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
}
