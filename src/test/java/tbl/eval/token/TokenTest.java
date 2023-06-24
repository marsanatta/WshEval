package tbl.eval.token;

import org.junit.jupiter.api.Test;
import tbl.eval.exceptions.BuildTokenException;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void testBuilder_NumberNullValue() {
        assertThrows(BuildTokenException.class, () -> Token.builder().type(TokenType.NUM).build());
    }

    @Test
    void testBuilder_VariableNullValue() {
        assertThrows(BuildTokenException.class, () -> Token.builder().type(TokenType.VAR).build());
    }
}