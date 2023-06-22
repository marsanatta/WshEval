package tbl.eval.ast;

import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Unary Operation Node (+, -, ++, --)
 */
public class UnaryOpNode implements TreeNode {
    private Token op;
    private TreeNode expr;

    public UnaryOpNode(Token op, TreeNode expr) {
        this.op = op;
        this.expr = expr;
    }

    public Token getOp() {
        return op;
    }

    public TreeNode getExpr() {
        return expr;
    }

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitUnaryOp(this);
    }
}
