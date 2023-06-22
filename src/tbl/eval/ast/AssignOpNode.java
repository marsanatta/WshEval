package tbl.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Assign operator node (=,+=,-=,*=,/=,%=)
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
    public Number accept(TreeVisitor visitor) throws UnknownVariableException {
        return visitor.visitAssignOpNode(this);
    }
}
