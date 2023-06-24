package tbl.eval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.exceptions.InvalidTokenException;
import tbl.eval.token.Token;
import tbl.eval.token.TokenType;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LexerTest {

    private Lexer lexer;

    @BeforeEach
    public void setup() {
        lexer = new Lexer();
    }

    @Test
    void testGetToken_skipWhitespaces() throws Exception {
        lexer.consume("            ");
        assertEquals(Token.builder().type(TokenType.EOF).build(), lexer.getToken());
    }

    @Test
    void testGetToken_Variable() throws Exception {
        String varName = "abcDef123zs";
        lexer.consume(varName);
        assertEquals(Token.builder().type(TokenType.VAR).value(varName).build(), lexer.getToken());
    }

    @Test
    void testGetToken_Number() throws Exception {
        String num = "123.456";
        lexer.consume(num);
        assertEquals(Token.builder().type(TokenType.NUM).value(num).build(), lexer.getToken());
    }

    @Test
    void testGetToken_NumberScientificNotion() throws Exception {
        String num = "1.3e10";
        lexer.consume(num);
        assertEquals(Token.builder().type(TokenType.NUM).value(num).build(), lexer.getToken());
    }

    @Test
    void testGetToken_NumberScientificNotionPlus() throws Exception {
        String num = "1.3e+10";
        lexer.consume(num);
        assertEquals(Token.builder().type(TokenType.NUM).value(num).build(), lexer.getToken());
    }

    @Test
    void testGetToken_NumberNegScientificNotionNeg() throws Exception {
        String num = "1.3e-10";
        lexer.consume(num);
        assertEquals(Token.builder().type(TokenType.NUM).value(num).build(), lexer.getToken());
    }

    @Test
    void testGetToken_NumberStartZero() throws Exception {
        String num = "0123";
        lexer.consume(num);
        assertThrows(InvalidTokenException.class, () -> lexer.getToken());
    }

    @Test
    void testGetToken_NumberMultiDecimalPoint() throws Exception {
        String num = "1.2.3";
        lexer.consume(num);
        assertThrows(InvalidTokenException.class, () -> lexer.getToken());
    }

    @Test
    void testGetToken_NumberMultiScientificNotion() throws Exception {
        String num = "10E123E56";
        lexer.consume(num);
        assertThrows(InvalidTokenException.class, () -> lexer.getToken());
    }

    @Test
    void testGetToken_PLUS() throws Exception {
        String text = "+";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.PLUS).build(), lexer.getToken());
    }

    @Test
    void testGetToken_DOUBLE_PLUS() throws Exception {
        String text = "++";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.DOUBLE_PLUS).build(), lexer.getToken());
    }

    @Test
    void testGetToken_MINUS() throws Exception {
        String text = "-";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.MINUS).build(), lexer.getToken());
    }

    @Test
    void testGetToken_DOUBLE_MINUS() throws Exception {
        String text = "--";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.DOUBLE_MINUS).build(), lexer.getToken());
    }

    @Test
    void testGetToken_DIV() throws Exception {
        String text = "/";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.DIV).build(), lexer.getToken());
    }

    @Test
    void testGetToken_MUL() throws Exception {
        String text = "*";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.MUL).build(), lexer.getToken());
    }

    @Test
    void testGetToken_REM() throws Exception {
        String text = "%";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.REM).build(), lexer.getToken());
    }

    @Test
    void testGetToken_ASSIGN() throws Exception {
        String text = "=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_ADD_ASSIGN() throws Exception {
        String text = "+=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.ADD_ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_SUB_ASSIGN() throws Exception {
        String text = "-=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.SUB_ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_MUL_ASSIGN() throws Exception {
        String text = "*=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.MUL_ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_DIV_ASSIGN() throws Exception {
        String text = "/=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.DIV_ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_REM_ASSIGN() throws Exception {
        String text = "%=";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.REM_ASSIGN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_LPAREN() throws Exception {
        String text = "(";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.LPAREN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_RPAREN() throws Exception {
        String text = ")";
        lexer.consume(text);
        assertEquals(Token.builder().type(TokenType.RPAREN).build(), lexer.getToken());
    }

    @Test
    void testGetToken_InvalidCharacter() {
        String text = "$";
        lexer.consume(text);
        assertThrows(InvalidTokenException.class, () -> lexer.getToken());
    }

}