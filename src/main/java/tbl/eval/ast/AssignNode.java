package tbl.eval.ast;

import tbl.eval.token.Token;
import tbl.eval.number.Number;

public class AssignNode implements TreeNode {

    private TreeNode left;
    private Token op; // =, +=, -=, *=, /=, %=
    private TreeNode right;

    public AssignNode(TreeNode left, Token op, TreeNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    public TreeNode getLeft() {
        return left;
    }

    public Token getOp() {
        return op;
    }

    public TreeNode getRight() {
        return right;
    }

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitAssign(this);
    }
}
