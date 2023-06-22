package tbl.eval.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

@Getter
@AllArgsConstructor
public class AssignNode implements TreeNode {

    private final TreeNode left;
    private final Token op; // =, +=, -=, *=, /=, %=
    private final TreeNode right;

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitAssign(this);
    }
}
