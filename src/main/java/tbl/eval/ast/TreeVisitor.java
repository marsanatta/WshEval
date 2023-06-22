package tbl.eval.ast;

import tbl.eval.exceptions.UnknownVariableException;
import tbl.eval.number.Number;

/**
 * Visitor of abstract syntax tree
 */
public interface TreeVisitor {
    Number visitBinaryOpNode(BinaryOpNode node) throws UnknownVariableException;

    Number visitUnaryOpNode(UnaryOpNode node) throws UnknownVariableException;

    Number visitNumberNode(NumberNode node);

    Number visitVarNode(VarNode varNode) throws UnknownVariableException;

    Number visitAssignOpNode(AssignOpNode assignOpNode) throws UnknownVariableException;
}
