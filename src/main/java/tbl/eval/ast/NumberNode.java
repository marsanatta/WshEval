package tbl.eval.ast;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.BuildNumberNodeException;
import tbl.eval.token.Token;
import tbl.eval.number.Number;

import java.math.BigDecimal;

/**
 * Number Node is node for a Number
 */
@Getter
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class NumberNode implements TreeNode {

    @NonNull
    private final Token token;
    @NonNull
    private final Number number;

    public static Builder builder() {
        return new Builder();

    }

    public Number accept(TreeVisitor visitor) {
        return visitor.visitNumberNode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NumberNode) {
            NumberNode another = (NumberNode) obj;
            return token.equals(another.token) && number.equals(another.number);
        } else {
            return false;
        }
    }

    public static class Builder {
        private Token token;

        public Builder token(Token token) {
            this.token = token;
            return this;
        }

        public NumberNode build() {

            if (token == null) {
                throw new BuildNumberNodeException("token must be non-null");
            }
            String numStr = token.getValue();
            Number number = Number.valueOf(numStr);
            return new NumberNode(token, number);
        }

    }

}
