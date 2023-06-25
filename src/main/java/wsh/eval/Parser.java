package wsh.eval;

import lombok.NonNull;
import wsh.eval.ast.TreeNode;
import wsh.eval.ast.AssignOpNode;
import wsh.eval.ast.BinaryOpNode;
import wsh.eval.ast.NumberNode;
import wsh.eval.ast.UnaryOpNode;
import wsh.eval.ast.VarNode;
import wsh.eval.exceptions.InvalidSyntaxException;
import wsh.eval.exceptions.InvalidTokenException;
import wsh.eval.token.Token;
import wsh.eval.token.TokenType;

import java.util.Set;

/**
 * Parse tokens generated by lexer into abstract syntax tree
 */
public class Parser {
    private static final Set<TokenType> TOKEN_TYPE_ASSIGN_SET = Set.of(TokenType.ASSIGN, TokenType.ADD_ASSIGN,
            TokenType.SUB_ASSIGN, TokenType.MUL_ASSIGN, TokenType.DIV_ASSIGN, TokenType.REM_ASSIGN);
    private static final Set<TokenType> TOKEN_TYPE_EXPR_OP_SET = Set.of(TokenType.PLUS, TokenType.MINUS);
    private static final Set<TokenType> TOKEN_TYPE_TERM_OP_SET = Set.of(TokenType.MUL, TokenType.DIV, TokenType.REM);
    private static final Set<TokenType> TOKEN_TYPE_UNARY_INCR_DECR_SET = Set.of(
            TokenType.DOUBLE_PLUS, TokenType.DOUBLE_MINUS);

    private final Lexer lexer;
    private Token curToken;

    public Parser(@NonNull Lexer lexer) {
        this.lexer = lexer;
    }

    /**
     * Compare the current token with the expected token types.
     * If they match, then "eat" the current token, and assign curToken to next token.
     * Otherwise, throw exception because of invalid syntax
     * @param types expected token types
     * @return the eaten token
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private Token eat(Set<TokenType> types) throws InvalidTokenException, InvalidSyntaxException {
        Token eatenNode = curToken;
        if (types.contains(curToken.getType())) {
            curToken = lexer.getToken();
        } else {
            throw new InvalidSyntaxException(
                    String.format("Invalid syntax at token: %s expect any from %s", curToken, types)
            );
        }
        return eatenNode;
    }

    /**
     * Compare the current token with the expected token type.
     * If they match, then "eat" the current token, and assign curToken to next token.
     * Otherwise, throw exception because of invalid syntax
     * @param type expected token type
     * @return the eaten token
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private Token eat(TokenType type) throws InvalidTokenException, InvalidSyntaxException {
        Token eatenNode = curToken;
        if (curToken.getType() == type) {
            curToken = lexer.getToken();
        } else {
            throw new InvalidSyntaxException(String.format("Invalid syntax at token: %s expect %s", curToken, type));
        }
        return eatenNode;
    }

    /**
     * Parse an assign expression
     * @return Assign expression node
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private TreeNode parseAssignExpr() throws InvalidSyntaxException, InvalidTokenException {
        VarNode left = parseVariable();
        Token assignOp = curToken;
        eat(TOKEN_TYPE_ASSIGN_SET);
        TreeNode right = expr();
        return AssignOpNode.builder()
                .left(left)
                .op(assignOp)
                .right(right)
                .build();
    }

    /**
     * Parse a expr node
     * expr: term ((PLUS | MINUS) term)*
     * @return tree node
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private TreeNode expr() throws InvalidTokenException, InvalidSyntaxException {
        TreeNode node = term();
        while (TOKEN_TYPE_EXPR_OP_SET.contains(curToken.getType())) {
            Token opToken = curToken;
            eat(TOKEN_TYPE_EXPR_OP_SET);
            node = BinaryOpNode.builder()
                    .left(node)
                    .op(opToken)
                    .right(term())
                    .build();
        }
        return node;
    }

    /**
     * Parse a term node
     * term: factor ((MUL | DIV) factor)*
     * @return tree node
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private TreeNode term() throws InvalidTokenException, InvalidSyntaxException {
        TreeNode node = factor();
        while (TOKEN_TYPE_TERM_OP_SET.contains(curToken.getType())) {
            Token opToken = curToken;
            eat(TOKEN_TYPE_TERM_OP_SET);
            node = BinaryOpNode.builder()
                    .left(node)
                    .op(opToken)
                    .right(factor())
                    .build();
        }
        return node;
    }

    /**
     * Parse a factor node
     * factor : PLUS factor | MINUS factor | NUMBER | LPAREN expr RPAREN | VARIABLE(with pre/post increment/decrement)
     * @return tree node
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private TreeNode factor() throws InvalidTokenException, InvalidSyntaxException {
        Token token = curToken;
        switch (token.getType()) {
            case PLUS:
                eat(TokenType.PLUS);
                return UnaryOpNode.builder().op(token).right(factor()).build();
            case MINUS:
                eat(TokenType.MINUS);
                return UnaryOpNode.builder().op(token).right(factor()).build();
            case NUM:
                eat(TokenType.NUM);
                return NumberNode.builder().token(token).build();
            case LPAREN:
                eat(TokenType.LPAREN);
                TreeNode node = expr();
                eat(TokenType.RPAREN);
                return node;
            case VAR:
            case DOUBLE_PLUS: // pre increment
            case DOUBLE_MINUS: // pre decrement
                return parseVariable();
            default:
                throw new InvalidSyntaxException(
                        String.format("Encounter invalid token %s while parsing. Expect a factor", curToken)
                );
        }
    }

    /**
     * Parse the input text from tokens generated by the given Lexer
     * @return Root node of abstract syntax tree
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    public TreeNode parse() throws InvalidTokenException, InvalidSyntaxException {
        this.curToken = lexer.getToken(); // set initial token
        boolean isAssignExpr =
                (TOKEN_TYPE_UNARY_INCR_DECR_SET.contains(curToken.getType()) || curToken.getType() == TokenType.VAR)
                && lexer.getText().contains("=");
        TreeNode root;
        if (isAssignExpr) {
            root = parseAssignExpr();
        } else {
            root = expr();
        }
        if (curToken.getType() != TokenType.EOF) {
            throw new InvalidSyntaxException(
                    String.format("Encounter invalid token %s while parsing. Expect an EOF", curToken)
            );
        }
        return root;
    }

    /**
     * Parse tokens to generate a variable node.
     * Variable node can have pre/post-increment/decrement operators
     * @return A variable node
     * @throws InvalidTokenException input has invalid token
     * @throws InvalidSyntaxException input has invalid syntax
     */
    private VarNode parseVariable() throws InvalidTokenException, InvalidSyntaxException {
        VarNode.Builder varNodeBuilder = VarNode.builder();
        // parse pre-increment/decrement operator
        Token preIncrDecrToken = null;
        if (TOKEN_TYPE_UNARY_INCR_DECR_SET.contains(curToken.getType())) {
            preIncrDecrToken = curToken;
            eat(TOKEN_TYPE_UNARY_INCR_DECR_SET);
            varNodeBuilder.preIncrDecrToken(preIncrDecrToken);
        }
        // parser variable
        Token varToken = eat(TokenType.VAR);
        varNodeBuilder
                .token(varToken);

        // parse post-increment/decrement operator
        if (TOKEN_TYPE_UNARY_INCR_DECR_SET.contains(curToken.getType())) {
            if (preIncrDecrToken != null) {
                throw new InvalidSyntaxException(
                        "Variable " + varToken + " have both pre and post increment/decrement operator"
                );
            }
            Token postIncrDecrToken = curToken;
            eat(TOKEN_TYPE_UNARY_INCR_DECR_SET);
            varNodeBuilder.postIncrDecrToken(postIncrDecrToken);
        }
        return varNodeBuilder.build();
    }

}