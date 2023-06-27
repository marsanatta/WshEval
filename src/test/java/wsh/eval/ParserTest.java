package wsh.eval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.tree.AssignOpNode;
import wsh.eval.tree.BinaryOpNode;
import wsh.eval.tree.NumberNode;
import wsh.eval.tree.UnaryOpNode;
import wsh.eval.tree.VarNode;
import wsh.eval.exceptions.InvalidSyntaxException;
import wsh.eval.token.Token;
import wsh.eval.token.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParserTest {

    private static final Token EOF_TOKEN = Token.builder().type(TokenType.EOF).build();

    @Mock
    private Lexer lexer;
    private Parser parser;

    @BeforeEach
    void setup() {
        parser = new Parser(lexer);
    }

    @Test
    void testParse_Factor_UnaryOpNode_Plus() throws Exception {
        Token op = Token.builder().type(TokenType.PLUS).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                op,
                numToken,
                EOF_TOKEN
        );
        UnaryOpNode node = (UnaryOpNode) parser.parse("mock");
        assertEquals(
                UnaryOpNode.builder()
                        .op(op)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Factor_UnaryOpNode_MINUS() throws Exception {
        Token op = Token.builder().type(TokenType.MINUS).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                op,
                numToken,
                EOF_TOKEN
        );
        UnaryOpNode node = (UnaryOpNode) parser.parse("mock");
        assertEquals(
                UnaryOpNode.builder()
                        .op(op)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Factor_UnaryOpNode_NotEndWithFactor() throws Exception {
        when(lexer.getToken()).thenReturn(
                Token.builder().type(TokenType.MINUS).build(),
                Token.builder().type(TokenType.ASSIGN).value("1").build(),
                EOF_TOKEN
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_Factor_NumberNode() throws Exception {
        Token numberToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                numberToken,
                EOF_TOKEN
        );
        NumberNode node = (NumberNode) parser.parse("mock");
        assertEquals(NumberNode.builder().token(numberToken).build(), node);
    }

    @Test
    void testParse_Factor_VariableNode() throws Exception {
        String varName = "a";
        Token varToken = Token.builder().type(TokenType.VAR).value(varName).build();
        when(lexer.getText()).thenReturn(varName);
        when(lexer.getToken()).thenReturn(
                varToken,
                EOF_TOKEN
        );
        VarNode node = (VarNode) parser.parse("mock");
        assertEquals(VarNode.builder().token(varToken).build(), node);
    }

    @Test
    void testParse_Factor_VariableNode_PreIncrDecrToken() throws Exception {
        String varName = "a";
        when(lexer.getText()).thenReturn(varName);
        Token preIncrDecrToken = Token.builder()
                .type(TokenType.DOUBLE_PLUS)
                .build();
        Token varToken = Token.builder()
                .type(TokenType.VAR)
                .value(varName)
                .build();
        when(lexer.getToken()).thenReturn(
                preIncrDecrToken,
                varToken,
                EOF_TOKEN
        );
        VarNode node = (VarNode) parser.parse("mock");
        assertEquals(VarNode.builder().token(varToken)
                .preIncrDecrToken(preIncrDecrToken).build(), node);
    }

    @Test
    void testParse_Factor_VariableNode_PostIncrDecrToken() throws Exception {
        String varName = "a";
        when(lexer.getText()).thenReturn(varName);
        Token postIncrDecrToken = Token.builder()
                .type(TokenType.DOUBLE_PLUS)
                .build();
        Token varToken = Token.builder()
                .type(TokenType.VAR)
                .value(varName)
                .build();
        when(lexer.getToken()).thenReturn(
                varToken,
                postIncrDecrToken,
                EOF_TOKEN
        );
        VarNode node = (VarNode) parser.parse("mock");
        assertEquals(VarNode.builder().token(varToken)
                .postIncrDecrToken(postIncrDecrToken).build(), node);
    }

    @Test
    void testParse_Factor_VariableNode_HashBothPrePostIncrDecrToken() throws Exception {
        String varName = "a";
        when(lexer.getText()).thenReturn(varName);
        Token preIncrDecrToken = Token.builder()
                .type(TokenType.DOUBLE_PLUS)
                .build();
        Token postIncrDecrToken = Token.builder()
                .type(TokenType.DOUBLE_MINUS)
                .build();
        Token varToken = Token.builder()
                .type(TokenType.VAR)
                .value(varName)
                .build();
        when(lexer.getToken()).thenReturn(
                preIncrDecrToken,
                varToken,
                postIncrDecrToken,
                EOF_TOKEN
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_Factor_Parenthesis() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.PLUS).build();
        when(lexer.getToken()).thenReturn(
                Token.builder().type(TokenType.LPAREN).build(),
                numToken1,
                op,
                numToken2,
                Token.builder().type(TokenType.RPAREN).build(),
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Factor_Parenthesis_WithoutRightParen() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.PLUS).build();
        when(lexer.getToken()).thenReturn(
                Token.builder().type(TokenType.LPAREN).build(),
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_Term_MUL() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.MUL).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Term_DIV() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.DIV).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Term_REM() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.REM).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Term_NotEndWithFactor() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token op = Token.builder().type(TokenType.MUL).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                EOF_TOKEN
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_Expr_PLUS() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.PLUS).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Expr_MINUS() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token numToken2 = Token.builder().type(TokenType.NUM).value("2").build();
        Token op = Token.builder().type(TokenType.MINUS).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                numToken2,
                EOF_TOKEN
        );
        BinaryOpNode node = (BinaryOpNode) parser.parse("mock");
        assertEquals(
                BinaryOpNode.builder()
                        .left(NumberNode.builder().token(numToken1).build())
                        .op(op)
                        .right(NumberNode.builder().token(numToken2).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Expr_NotEndWithTerm() throws Exception {
        Token numToken1 = Token.builder().type(TokenType.NUM).value("1").build();
        Token op = Token.builder().type(TokenType.MINUS).build();
        when(lexer.getToken()).thenReturn(
                numToken1,
                op,
                Token.builder().type(TokenType.ASSIGN).build(),
                EOF_TOKEN
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_NotStartWithFactor() throws Exception {
        when(lexer.getToken()).thenReturn(
                Token.builder().type(TokenType.MUL).build(),
                Token.builder().type(TokenType.VAR).value("name").build()
        );
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_Assign_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_ADD_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.ADD_ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_SUB_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.SUB_ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_MUL_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.MUL_ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_DIV_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.DIV_ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_REM_ASSIGN() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.REM_ASSIGN).build();
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                numToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + numToken.getValue());
        AssignOpNode node = (AssignOpNode) parser.parse("mock");
        assertEquals(
                AssignOpNode.builder()
                        .left(VarNode.builder().token(varToken).build())
                        .op(opToken)
                        .right(NumberNode.builder().token(numToken).build())
                        .build(),
                node
        );
    }

    @Test
    void testParse_Assign_NotEndWithExpr() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        Token opToken = Token.builder().type(TokenType.ASSIGN).build();
        Token notExprToken = Token.builder().type(TokenType.RPAREN).build();
        when(lexer.getToken()).thenReturn(
                varToken,
                opToken,
                notExprToken,
                EOF_TOKEN
        );
        when(lexer.getText()).thenReturn(varToken.getValue() + opToken.getValue() + notExprToken.getValue());
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }

    @Test
    void testParse_NotEndWithEOF() throws Exception {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        when(lexer.getToken()).thenReturn(
                varToken
        );
        when(lexer.getText()).thenReturn(varToken.getValue());
        assertThrows(InvalidSyntaxException.class, () -> parser.parse("mock"));
    }
}