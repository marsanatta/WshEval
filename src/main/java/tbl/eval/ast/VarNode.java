package tbl.eval.ast;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Variable Node
 */
@Getter
@Builder
public class VarNode implements TreeNode {
    @NonNull
    private final Token varToken;
    @NonNull
    private final String varName;
    private Token preIncrDecrToken;
    private Token postIncrDecrToken;

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitVar(this);
    }
}
