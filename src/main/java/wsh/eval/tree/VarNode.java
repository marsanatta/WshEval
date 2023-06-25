package wsh.eval.tree;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import wsh.eval.exceptions.BuildVarNodeException;
import wsh.eval.token.Token;
import wsh.eval.exceptions.VariableNotFoundException;
import wsh.eval.number.Number;

import java.util.Optional;

/**
 * Variable Node is node for variable
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class VarNode implements TreeNode {

    @NonNull
    private final Token token;
    @NonNull
    private final String varName;
    @NonNull
    private Optional<Token> preIncrDecrToken;
    @NonNull
    private Optional<Token> postIncrDecrToken;

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitVarNode(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VarNode) {
            VarNode another = (VarNode) obj;
            // pre increment/decrement token not equal
            if (preIncrDecrToken.isPresent() ^ another.preIncrDecrToken.isPresent()) {
                return false;
            }
            if (postIncrDecrToken.isPresent() ^ another.postIncrDecrToken.isPresent()) {
                return false;
            }
            if (preIncrDecrToken.isPresent() && another.preIncrDecrToken.isPresent()
                    && !preIncrDecrToken.get().equals(another.preIncrDecrToken.get())
            ) {
                return false;
            }
            if (postIncrDecrToken.isPresent() && another.postIncrDecrToken.isPresent()
                    && !postIncrDecrToken.get().equals(another.postIncrDecrToken.get())
            ) {
                return false;
            }
            return token.equals(another.token) && varName.equals(another.varName);
        } else {
            return false;
        }
    }

    public static class Builder {

        private Token token;
        private Token preIncrDecrToken;
        private Token postIncrDecrToken;

        public Builder token(Token token) {
            this.token = token;
            return this;
        }

        public Builder preIncrDecrToken(Token preIncrDecrToken) {
            this.preIncrDecrToken = preIncrDecrToken;
            return this;
        }

        public Builder postIncrDecrToken(Token postIncrDecrToken) {
            this.postIncrDecrToken = postIncrDecrToken;
            return this;
        }

        public VarNode build() {
            if (token == null) {
                throw new BuildVarNodeException("token must be non-null");
            }
            String varName = token.getValue();
            return new VarNode(token, varName, Optional.ofNullable(preIncrDecrToken),
                    Optional.ofNullable(postIncrDecrToken));
        }
    }

}
