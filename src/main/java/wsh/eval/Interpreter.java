package wsh.eval;

import lombok.Builder;
import lombok.Getter;
import wsh.eval.tree.AssignOpNode;
import wsh.eval.tree.BinaryOpNode;
import wsh.eval.tree.NumberNode;
import wsh.eval.tree.TreeNode;
import wsh.eval.tree.TreeVisitor;
import wsh.eval.tree.UnaryOpNode;
import wsh.eval.tree.VarNode;
import wsh.eval.exceptions.InvalidSyntaxException;
import wsh.eval.exceptions.UnknownTokenTypeException;
import wsh.eval.number.Number;
import wsh.eval.token.Token;
import wsh.eval.variable.VariableStore;
import lombok.NonNull;
import wsh.eval.exceptions.InvalidTokenException;
import wsh.eval.exceptions.VariableNotFoundException;


/**
 * Interpreter interprets the Abstracted Syntax Tree (AST) generated from the Parser
 * It implements TreeVisitor methods for AST node
 */
@Builder
@Getter
public class Interpreter implements TreeVisitor {

    /**
     * Parser to be used
     */
    @NonNull
    private final Parser parser;

    /**
     * Variable storage to be access
     */
    @NonNull
    private VariableStore varStore;

    public Interpreter(@NonNull Parser parser, @NonNull VariableStore varStore) {
        this.parser = parser;
        this.varStore = varStore;
    }

    /**
     * Handle binary operator node
     * @param node binary operator node
     * @return result
     * @throws VariableNotFoundException unknown variable in the expression
     */
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

    /**
     * Handle unary operator node
     * @param node bunary operator node
     * @return result
     * @throws VariableNotFoundException unknown variable in the expression
     */
    @Override
    public Number visitUnaryOpNode(UnaryOpNode node) throws VariableNotFoundException {
        Token op = node.getOp();
        switch (op.getType()) {
            case PLUS:
                return node.getRight().accept(this);
            case MINUS:
                Number number = node.getRight().accept(this);
                return number.negate();
            default:
                throw new UnknownTokenTypeException("Unknown unary operator type:" + op.getType());
        }
    }

    /**
     * Handle number node
     * @param node number node
     * @return result
     */
    @Override
    public Number visitAssignOpNode(AssignOpNode node) throws VariableNotFoundException {
        String varName = node.getLeft().getVarName();
        Token op = node.getOp();
        Number newVarValue;
        TreeNode rightExpr = node.getRight();
        switch (op.getType()) {
            case ASSIGN:
                newVarValue = rightExpr.accept(this);
                break;
            case ADD_ASSIGN:
                newVarValue = varStore.get(varName).add(rightExpr.accept(this));
                break;
            case SUB_ASSIGN:
                newVarValue = varStore.get(varName).subtract(rightExpr.accept(this));
                break;
            case MUL_ASSIGN:
                newVarValue = varStore.get(varName).multiply(rightExpr.accept(this));
                break;
            case DIV_ASSIGN:
                newVarValue = varStore.get(varName).divide(rightExpr.accept(this));
                break;
            case REM_ASSIGN:
                newVarValue = varStore.get(varName).remainder(rightExpr.accept(this));
                break;
            default:
                throw new UnknownTokenTypeException("Unknown assign operator type: " + op.getType());
        }
        varStore.set(varName, newVarValue);
        return newVarValue;
    }

    /**
     * Handle number node
     * @param node number node
     * @return result
     */
    @Override
    public Number visitNumberNode(NumberNode node) {
        return node.getNumber();
    }

    /**
     * Get new variable value with the pre/post increment/decrement operators
     * @param op operator token
     * @param varValue original variable value
     * @return new variable value
     */
    private Number getNewVarValue(Token op, Number varValue) {
        switch (op.getType()) {
            case DOUBLE_PLUS:
                return varValue.add(Number.valueOf(1L));
            case DOUBLE_MINUS:
                return varValue.subtract(Number.valueOf(1L));
            default:
                throw new UnknownTokenTypeException("Unknown unary increment/decrement token type:" + op.getType());
        }
    }

    /**
     * Handle variable node
     * @param varNode variable node
     * @return result
     * @throws VariableNotFoundException unknown variable in the expression
     */
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

    /**
     * Interpret the given text
     * @param text input text
     * @return evaluation result
     * @throws InvalidTokenException invalid token exception
     * @throws InvalidSyntaxException invalid syntax exception
     * @throws VariableNotFoundException variable not found exception
     */
    public Number interpret(String text) throws InvalidTokenException, InvalidSyntaxException,
            VariableNotFoundException {
        TreeNode root = parser.parse(text);
        if (root == null) {
            return null;
        }
        return root.accept(this);
    }

}
