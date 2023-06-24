package tbl.eval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.exceptions.InvalidTokenException;
import tbl.eval.number.NumberType;
import tbl.eval.token.Token;
import tbl.eval.token.TokenType;
import tbl.eval.number.Number;


import java.math.BigDecimal;
import java.math.RoundingMode;

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
        Token t = lexer.getToken();
        assertEquals(TokenType.EOF, t.getType());
    }

    @Test
    void testGetToken_Variable() throws Exception {
        String varName = "abcDef123zs";
        lexer.consume(varName);
        Token t = lexer.getToken();
        assertEquals(TokenType.VAR, t.getType());
        assertEquals(varName, t.getValue());
    }

    @Test
    void testGetToken_NumberFloatOverflow() throws Exception {
        String num = BigDecimal.valueOf(Double.MAX_VALUE).add(new BigDecimal("1.1")).toString();
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.BIG_DECIMAL, new BigDecimal(num)), t.getValue());
    }

    @Test
    void testGetToken_NumberFloatUnderflow() throws Exception {
        String num = (BigDecimal.valueOf(Double.MIN_VALUE).divide(BigDecimal.TEN, RoundingMode.HALF_UP)).toString();
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.BIG_DECIMAL, new BigDecimal(num)), t.getValue());
    }

    @Test
    void testGetToken_NumberFloat() throws Exception {
        String num = "1.0";
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.DOUBLE, 1.0), t.getValue());
    }

    @Test
    void testGetToken_NumberScientificNotion() throws Exception {
        String num = "1.3e10";
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.DOUBLE, 1.3e10), t.getValue());
    }

    @Test
    void testGetToken_NumberScientificNotionPlus() throws Exception {
        String num = "1.3e+10";
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.DOUBLE, 1.3e+10), t.getValue());
    }

    @Test
    void testGetToken_NumberNegScientificNotionNeg() throws Exception {
        String num = "1.3e-10";
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.DOUBLE, 1.3e-10), t.getValue());
    }

    @Test
    void testGetToken_NumberIntegerOverflow() throws Exception {
        String num = (BigDecimal.valueOf(Long.MAX_VALUE).add(BigDecimal.ONE)).toString();
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.BIG_DECIMAL, new BigDecimal(num)), t.getValue());
    }

    @Test
    void testGetToken_NumberIntegerUnderflow() throws Exception {
        String num = (BigDecimal.valueOf(Long.MIN_VALUE).subtract(BigDecimal.ONE)).toString();
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.MINUS, t.getType());
        t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.BIG_DECIMAL, new BigDecimal(num).negate()), t.getValue());
    }

    @Test
    void testGetToken_NumberInteger() throws Exception {
        String num = "123";
        lexer.consume(num);
        Token t = lexer.getToken();
        assertEquals(TokenType.NUM, t.getType());
        assertEquals(new Number(NumberType.LONG, 123L), t.getValue());
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
        Token t = lexer.getToken();
        assertEquals(TokenType.PLUS, t.getType());
    }

    @Test
    void testGetToken_DOUBLE_PLUS() throws Exception {
        String text = "++";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.DOUBLE_PLUS, t.getType());
    }

    @Test
    void testGetToken_MINUS() throws Exception {
        String text = "-";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.MINUS, t.getType());
    }

    @Test
    void testGetToken_DOUBLE_MINUS() throws Exception {
        String text = "--";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.DOUBLE_MINUS, t.getType());
    }

    @Test
    void testGetToken_DIV() throws Exception {
        String text = "/";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.DIV, t.getType());
    }

    @Test
    void testGetToken_MUL() throws Exception {
        String text = "*";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.MUL, t.getType());
    }

    @Test
    void testGetToken_REM() throws Exception {
        String text = "%";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.REM, t.getType());
    }

    @Test
    void testGetToken_ASSIGN() throws Exception {
        String text = "=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.ASSIGN, t.getType());
    }

    @Test
    void testGetToken_ADD_ASSIGN() throws Exception {
        String text = "+=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.ADD_ASSIGN, t.getType());
    }

    @Test
    void testGetToken_SUB_ASSIGN() throws Exception {
        String text = "-=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.SUB_ASSIGN, t.getType());
    }

    @Test
    void testGetToken_MUL_ASSIGN() throws Exception {
        String text = "*=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.MUL_ASSIGN, t.getType());
    }

    @Test
    void testGetToken_DIV_ASSIGN() throws Exception {
        String text = "/=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.DIV_ASSIGN, t.getType());
    }

    @Test
    void testGetToken_REM_ASSIGN() throws Exception {
        String text = "%=";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.REM_ASSIGN, t.getType());
    }

    @Test
    void testGetToken_LPAREN() throws Exception {
        String text = "(";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.LPAREN, t.getType());
    }

    @Test
    void testGetToken_RPAREN() throws Exception {
        String text = ")";
        lexer.consume(text);
        Token t = lexer.getToken();
        assertEquals(TokenType.RPAREN, t.getType());
    }

    @Test
    void testGetToken_InvalidCharacter() {
        String text = "$";
        lexer.consume(text);
        assertThrows(InvalidTokenException.class, () -> lexer.getToken());
    }

}