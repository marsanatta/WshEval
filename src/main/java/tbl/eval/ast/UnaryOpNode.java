package tbl.eval.ast;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Unary Operation Node (+, -, ++, --)
 */
@Getter
@Builder
@AllArgsConstructor
public class UnaryOpNode implements TreeNode {
    private final Token op;
    private final TreeNode expr;

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitUnaryOp(this);
    }
}
