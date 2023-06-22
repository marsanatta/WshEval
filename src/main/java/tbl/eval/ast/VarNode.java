package tbl.eval.ast;

import tbl.eval.token.Token;
import tbl.eval.number.Number;

/**
 * Variable Node
 */
public class VarNode implements TreeNode {
    private Token varToken;
    private String varName;
    private Token preIncrDecrToken;
    private Token postIncrDecrToken;

    public VarNode(Token varToken, String varName) {
        this.varToken = varToken;
        this.varName = varName;
    }

    public Token getVarToken() {
        return varToken;
    }

    public String getVarName() {
        return varName;
    }

    public Token getPreIncrDecrToken() {
        return preIncrDecrToken;
    }

    public Token getPostIncrDecrToken() {
        return postIncrDecrToken;
    }

    public void setPreIncrDecrToken(Token preIncrDecrToken) {
        this.preIncrDecrToken = preIncrDecrToken;
    }

    public void setPostIncrDecrToken(Token postIncrDecrToken) {
        this.postIncrDecrToken = postIncrDecrToken;
    }

    @Override
    public Number accept(TreeVisitor visitor) {
        return visitor.visitVar(this);
    }
}
