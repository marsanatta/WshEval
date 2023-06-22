package tbl.eval.ast;

import lombok.Getter;
import lombok.NonNull;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Number Node.
 */
@Getter
public class NumberNode implements TreeNode {

    @NonNull
    private final Token token;
    @NonNull
    private final Number number;

    public NumberNode(@NonNull Token token) {
        this.token = token;
        this.number = (Number)(token.getValue());
    }

    public Number accept(TreeVisitor visitor) {
        return visitor.visitNumberNode(this);
    }


}
