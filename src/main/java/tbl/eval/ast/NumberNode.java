package tbl.eval.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Number Node.
 */
@Getter
public class NumberNode implements TreeNode {
    private Token token;
    private Number number;
    public NumberNode(Token token) {
        this.token = token;
        this.number = (Number)(token.getValue());
    }

    public Number accept(TreeVisitor visitor) {
        return visitor.visitNumber(this);
    }


}
