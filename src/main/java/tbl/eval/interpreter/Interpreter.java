package tbl.eval.interpreter;

import tbl.eval.ast.AssignOpNode;
import tbl.eval.ast.BinaryOpNode;
import tbl.eval.ast.NumberNode;
import tbl.eval.ast.TreeNode;
import tbl.eval.ast.TreeVisitor;
import tbl.eval.ast.UnaryOpNode;
import tbl.eval.ast.VarNode;
import tbl.eval.exceptions.InvalidSyntaxException;
import tbl.eval.exceptions.UnknownTokenTypeException;
import tbl.eval.lexer.Lexer;
import tbl.eval.number.Number;
import tbl.eval.number.NumberType;
import tbl.eval.parser.Parser;
import tbl.eval.token.Token;
import tbl.eval.variable.VariableStore;
import lombok.NonNull;
import tbl.eval.exceptions.InvalidTokenException;
import tbl.eval.exceptions.UnknownVariableException;

/**
 * Interpreter interprets the Abstracted Syntax Tree (AST) generated from the Parser
 * It implements TreeVisitor methods for AST node
 */
public class Interpreter implements TreeVisitor {

    @NonNull
    private final Lexer lexer;

    @NonNull
    private final Parser parser;
    private VariableStore varStore;
    public Interpreter(@NonNull Lexer lexer, @NonNull Parser parser, @NonNull VariableStore varStore) {
        this.lexer = lexer;
        this.parser = parser;
        this.varStore = varStore;
    }

    @Override
    public Number visitBinaryOpNode(BinaryOpNode node) throws UnknownVariableException {
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
    public Number visitUnaryOpNode(UnaryOpNode node) throws UnknownVariableException {
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
    public Number visitAssignOpNode(AssignOpNode assignOpNode) throws UnknownVariableException {
        String varName = assignOpNode.getLeft().getVarName();
        Token op = assignOpNode.getOp();
        Number rightExpr = assignOpNode.getRight().accept(this);
        Number newVarValue = null;
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

    @Override
    public Number visitVarNode(VarNode varNode) throws UnknownVariableException {
        String varName = varNode.getVarName();
        Number varValue = varStore.get(varName);
        Number evalValue = varValue;
        if (varNode.getPreIncrDecrToken().isPresent()) {
            Token op = varNode.getPreIncrDecrToken().get();
            Number newVarValue = null;
            switch (op.getType()) {
                case PRE_INCREMENT:
                    newVarValue = varValue.add(new Number(NumberType.LONG, 1L));
                    break;
                case PRE_DECREMENT:
                    newVarValue = varValue.subtract(new Number(NumberType.LONG, 1L));
                    break;
                default:
                    throw new UnknownTokenTypeException("Unknown pre increment/decrement token type:" + op.getType());
            }
            varStore.set(varName, newVarValue);
            evalValue = newVarValue; // eval value need to be new variable value
        } else if (varNode.getPostIncrDecrToken().isPresent()) {
            Token op = varNode.getPostIncrDecrToken().get();
            Number newVarValue = null;
            switch (op.getType()) {
                case POST_INCREMENT:
                    newVarValue = varValue.add(new Number(NumberType.LONG, 1L));
                    break;
                case POST_DECREMENT:
                    newVarValue = varValue.subtract(new Number(NumberType.LONG, 1L));
                    break;
                default:
                    throw new UnknownTokenTypeException("Unknown pre increment/decrement token type:" + op.getType());
            }
            varStore.set(varName, newVarValue);
        }
        return evalValue;
    }

    public Number interpret(String text) throws InvalidTokenException, InvalidSyntaxException, UnknownVariableException {
        lexer.consume(text);
        TreeNode root = parser.parse();
        if (root == null)
            return null;
        return root.accept(this);
    }

}
