package tbl.eval.ast;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.number.Number;
import tbl.eval.token.Token;
import tbl.eval.token.TokenType;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberNodeTest {

    @Test
    public void testAccept() {
        NumberNode node = NumberNode.builder()
                .token(Token.builder().type(TokenType.NUM).value("1").build())
                .build();
        TreeVisitor visitor = mock();
        when(visitor.visitNumberNode(any(NumberNode.class))).thenReturn(Number.valueOf(1L));
        Number number = node.accept(visitor);
        assertNotNull(number);
    }

    @Test
    void testEquals() {
        Token numToken = Token.builder().type(TokenType.NUM).value("1").build();
        NumberNode node1 = NumberNode.builder().token(numToken).build();
        NumberNode node2 = NumberNode.builder().token(numToken).build();
        assertEquals(node1, node2);
    }


}