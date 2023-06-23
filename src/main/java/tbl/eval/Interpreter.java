package tbl.eval;

import lombok.Builder;
import lombok.Getter;
import tbl.eval.ast.AssignOpNode;
import tbl.eval.ast.BinaryOpNode;
import tbl.eval.ast.NumberNode;
import tbl.eval.ast.TreeNode;
import tbl.eval.ast.TreeVisitor;
import tbl.eval.ast.UnaryOpNode;
import tbl.eval.ast.VarNode;
import tbl.eval.exceptions.InvalidSyntaxException;
import tbl.eval.exceptions.UnknownTokenTypeException;
import tbl.eval.number.Number;
import tbl.eval.number.NumberType;
import tbl.eval.token.Token;
import tbl.eval.variable.VariableStore;
import lombok.NonNull;
import tbl.eval.exceptions.InvalidTokenException;
import tbl.eval.exceptions.VariableNotFoundException;

/**
 * Interpreter interprets the Abstracted Syntax Tree (AST) generated from the Parser
 * It implements TreeVisitor methods for AST node
 */
@Builder
@Getter
public class Interpreter implements TreeVisitor {

    @NonNull
    private final Lexer lexer;
    @NonNull
    private final Parser parser;
    @NonNull
    private VariableStore varStore;

    public Interpreter(@NonNull Lexer lexer, @NonNull Parser parser, @NonNull VariableStore varStore) {
        this.lexer = lexer;
        this.parser = parser;
        this.varStore = varStore;
    }

    @Override
    public Number visitBinaryOpNode(BinaryOpNode node) throws VariableNotFoundException {
        Number left = node.getLeft().accept(this);
        Token op = node.getOp();
        Number right = node.getRight().accept(this);
        switch (op.getType()) {
            case PLUS:
                return left.add(right);
            case MINUS:
                return left.subtract(right);
            case MUL:
                return left.multiply(right);
            case DIV:
                return left.divide(right);
            case REM:
                return left.remainder(right);
            default:
                throw new UnknownTokenTypeException("Unknown binary operator type:" + op.getType());
        }
    }

    @Override
    public Number visitUnaryOpNode(UnaryOpNode node) throws VariableNotFoundException {
        Token op = node.getOp();
        switch (op.getType()) {
            case PLUS:
                return node.getExpr().accept(this);
            case MINUS:
                Number number = node.getExpr().accept(this);
                return number.negate();
            default:
                throw new UnknownTokenTypeException("Unknown unary operator type:" + op.getType());
        }
    }

    @Override
    public Number visitAssignOpNode(AssignOpNode assignOpNode) throws VariableNotFoundException {
        String varName = assignOpNode.getLeft().getVarName();
        Token op = assignOpNode.getOp();
        Number rightExpr = assignOpNode.getRight().accept(this);
        Number newVarValue;
        switch (op.getType()) {
            case ASSIGN:
                newVarValue = rightExpr;
                break;
            case ADD_ASSIGN:
                newVarValue = varStore.get(varName).add(rightExpr);
                break;
            case SUB_ASSIGN:
                newVarValue = varStore.get(varName).subtract(rightExpr);
                break;
            case MUL_ASSIGN:
                newVarValue = varStore.get(varName).multiply(rightExpr);
                break;
            case DIV_ASSIGN:
                newVarValue = varStore.get(varName).divide(rightExpr);
                break;
            case REM_ASSIGN:
                newVarValue = varStore.get(varName).remainder(rightExpr);
                break;
            default:
                throw new UnknownTokenTypeException("Unknown assign operator type: " + op.getType());
        }
        varStore.set(varName, newVarValue);
        return newVarValue;
    }

    @Override
    public Number visitNumberNode(NumberNode node) {
        return node.getNumber();
    }

    private Number getNewVarValue(Token op, Number varValue) {
        switch (op.getType()) {
            case DOUBLE_PLUS:
                return varValue.add(new Number(NumberType.LONG, 1L));
            case DOUBLE_MINUS:
                return varValue.subtract(new Number(NumberType.LONG, 1L));
            default:
                throw new UnknownTokenTypeException("Unknown unary increment/decrement token type:" + op.getType());
        }
    }

    @Override
    public Number visitVarNode(VarNode varNode) throws VariableNotFoundException {
        String varName = varNode.getVarName();
        Number varValue = varStore.get(varName);
        Number evalValue = varValue;
        if (varNode.getPreIncrDecrToken().isPresent()) {
            Token op = varNode.getPreIncrDecrToken().get();
            Number newVarValue = getNewVarValue(op, varValue);
            varStore.set(varName, newVarValue);
            evalValue = newVarValue; // eval value need to be new variable value
        } else if (varNode.getPostIncrDecrToken().isPresent()) {
            Token op = varNode.getPostIncrDecrToken().get();
            Number newVarValue = getNewVarValue(op, varValue);
            varStore.set(varName, newVarValue);
        }
        return evalValue;
    }

    public Number interpret(String text) throws InvalidTokenException, InvalidSyntaxException, VariableNotFoundException {
        lexer.consume(text);
        TreeNode root = parser.parse();
        if (root == null)
            return null;
        return root.accept(this);
    }

}
