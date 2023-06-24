package tbl.eval.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
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
public class AssignOpNodeTest {

    @Test
    public void testAccept() throws Exception {
        TreeVisitor visitor = mock();
        when(visitor.visitAssignOpNode(any(AssignOpNode.class))).thenReturn(Number.valueOf(1L));
        AssignOpNode node = AssignOpNode.builder()
                .left(mock())
                .op(mock())
                .right(mock())
                .build();
        Number number = node.accept(visitor);
        assertNotNull(number);
    }


    @Test
    public void testEquals() {
        String varName = "a";
        VarNode varNode = VarNode.builder()
                .token(Token.builder().type(TokenType.VAR).value(varName).build())
                .build();
        TreeNode right = NumberNode.builder()
                .token(Token.builder().type(TokenType.NUM).value("1").build())
                .build();
        Token op = Token.builder().type(TokenType.ASSIGN).build();
        AssignOpNode node1 = AssignOpNode.builder()
                .left(varNode)
                .op(op)
                .right(right)
                .build();
        AssignOpNode node2 = AssignOpNode.builder()
                .left(varNode)
                .op(op)
                .right(right)
                .build();
        assertEquals(node1, node2);
    }


}