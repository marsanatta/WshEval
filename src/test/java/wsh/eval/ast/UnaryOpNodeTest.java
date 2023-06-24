package wsh.eval.ast;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.number.Number;
import wsh.eval.token.Token;
import wsh.eval.token.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UnaryOpNodeTest {

    @Test
    void testAccept() throws Exception {
        TreeVisitor visitor = mock();
        when(visitor.visitUnaryOpNode(any(UnaryOpNode.class))).thenReturn(Number.valueOf(1L));
        UnaryOpNode node = UnaryOpNode.builder().op(mock()).right(mock()).build();
        Number number = node.accept(visitor);
        assertNotNull(number);
    }

    @Test
    void testEquals() {
        Token op = Token.builder().type(TokenType.PLUS).build();
        TreeNode num = NumberNode.builder().token(Token.builder().type(TokenType.NUM).value("1").build()).build();
        UnaryOpNode node1 = UnaryOpNode.builder().op(op).right(num).build();
        UnaryOpNode node2 = UnaryOpNode.builder().op(op).right(num).build();
        assertEquals(node1, node2);
    }


}