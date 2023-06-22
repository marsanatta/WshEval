package tbl.eval.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Binary operator node (+,-,*,/)
 */
@Getter
@AllArgsConstructor
public class BinaryOpNode implements TreeNode {
    private final TreeNode left;
    private final Token op;

    private final TreeNode right;
    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitBinaryOp(this);
    }
}
