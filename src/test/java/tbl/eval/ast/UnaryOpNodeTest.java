package tbl.eval.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.number.Number;
import tbl.eval.number.NumberType;
import tbl.eval.token.Token;
import tbl.eval.token.TokenType;

import static org.junit.jupiter.api.Assertions.*;
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