package tbl.eval.ast;

import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.number.Number;

/**
 * Visitor of abstract syntax tree, which is capable of handling all kinds of TreeNode,
 * and resolve value from it
 */
public interface TreeVisitor {

    /**
     * Handle binary operator node
     * @param node binary operator node
     * @return result
     * @throws UnknownVariableException unknown variable in the expression
     */
    Number visitBinaryOpNode(BinaryOpNode node) throws UnknownVariableException;

    /**
     * Handle unary operator node
     * @param node bunary operator node
     * @return result
     * @throws UnknownVariableException unknown variable in the expression
     */
    Number visitUnaryOpNode(UnaryOpNode node) throws UnknownVariableException;

    /**
     * Handle number node
     * @param node number node
     * @return result
     */
    Number visitNumberNode(NumberNode node);

    /**
     * Handle variable node
     * @param varNode variable node
     * @return result
     * @throws UnknownVariableException unknown variable in the expression
     */
    Number visitVarNode(VarNode varNode) throws UnknownVariableException;

    /**
     * Handle assign operator node
     * @param assignOpNode assign operator node
     * @return result
     * @throws UnknownVariableException unknown variable in the expression
     */
    Number visitAssignOpNode(AssignOpNode assignOpNode) throws UnknownVariableException;
}
