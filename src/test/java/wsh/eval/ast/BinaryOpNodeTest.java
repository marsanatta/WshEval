package wsh.eval.ast;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.number.Number;
import wsh.eval.token.Token;
import wsh.eval.token.TokenType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BinaryOpNodeTest {
    @Test
    void testAccept() throws Exception {
        TreeVisitor visitor = mock();
        when(visitor.visitBinaryOpNode(any(BinaryOpNode.class))).thenReturn(Number.valueOf(1L));
        BinaryOpNode node = BinaryOpNode.builder().left(mock()).op(mock()).right(mock()).build();
        Number number = node.accept(visitor);
        assertNotNull(number);
    }

    @Test
    void testEquals() {
        TreeNode left = NumberNode.builder()
                .token(Token.builder().type(TokenType.NUM).value("1").build())
                .build();
        Token op = Token.builder().type(TokenType.PLUS).build();
        TreeNode right = NumberNode.builder()
                .token(Token.builder().type(TokenType.NUM).value("1").build())
                .build();

        BinaryOpNode node1 = BinaryOpNode.builder()
                .left(left)
                .op(op)
                .right(right)
                .build();

        BinaryOpNode node2 = BinaryOpNode.builder()
                .left(left)
                .op(op)
                .right(right)
                .build();

        assertEquals(node1, node2);
    }
}