package tbl.eval.ast;

import tbl.eval.number.Number;

/**
 * Visitor of abstract syntax tree
 */
public interface TreeVisitor {
    Number visitBinaryOp(BinaryOpNode node);

    Number visitUnaryOp(UnaryOpNode node);

    Number visitNumber(NumberNode node);

    Number visitVar(VarNode varNode);

    Number visitAssign(AssignNode assignNode);
}
