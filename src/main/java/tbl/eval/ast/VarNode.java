package tbl.eval.ast;

import tbl.eval.token.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import tbl.eval.exceptions.VariableNotFoundException;
import tbl.eval.number.Number;

import java.util.Optional;

/**
 * Variable Node is node for variable
 */
@Getter
@Builder
public class VarNode implements TreeNode {
    @NonNull
    private final Token token;
    @NonNull
    private final String varName;
    @Builder.Default
    private Optional<Token> preIncrDecrToken = Optional.empty();
    @Builder.Default
    private Optional<Token> postIncrDecrToken = Optional.empty();

    @Override
    public Number accept(TreeVisitor visitor) throws VariableNotFoundException {
        return visitor.visitVarNode(this);
    }
}
