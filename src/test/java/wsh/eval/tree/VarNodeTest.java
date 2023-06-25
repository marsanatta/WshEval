package wsh.eval.tree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import wsh.eval.exceptions.BuildVarNodeException;
import wsh.eval.number.Number;
import wsh.eval.token.Token;
import wsh.eval.token.TokenType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VarNodeTest {
    @Test
    void testAccept() throws Exception {
        TreeVisitor visitor = mock();
        VarNode node = VarNode.builder()
                .token(Token.builder().type(TokenType.VAR).value("a").build())
                .build();
        when(visitor.visitVarNode(any(VarNode.class))).thenReturn(Number.valueOf(1L));
        Number number = node.accept(visitor);
        assertNotNull(number);
    }

    @Test
    void testEquals() {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        VarNode node1 = VarNode.builder().token(varToken).build();
        VarNode node2 = VarNode.builder().token(varToken).build();
        assertEquals(node1, node2);
    }

    @Test
    void testEquals_InconsistentPreToken_Null() {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        VarNode node1 = VarNode.builder().token(varToken).preIncrDecrToken(mock()).build();
        VarNode node2 = VarNode.builder().token(varToken).build();
        assertNotEquals(node1, node2);
    }

    @Test
    void testEquals_InconsistentPreToken_Value() {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        VarNode node1 = VarNode.builder().token(varToken)
                .preIncrDecrToken(Token.builder().type(TokenType.DOUBLE_PLUS).build())
                .build();
        VarNode node2 = VarNode.builder().token(varToken)
                .preIncrDecrToken(Token.builder().type(TokenType.DOUBLE_MINUS).build())
                .build();
        assertNotEquals(node1, node2);
    }

    @Test
    void testEquals_InconsistentPostToken_Null() {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        VarNode node1 = VarNode.builder().token(varToken).postIncrDecrToken(mock()).build();
        VarNode node2 = VarNode.builder().token(varToken).build();
        assertNotEquals(node1, node2);
    }

    @Test
    void testEquals_InconsistentPostToken_Value() {
        Token varToken = Token.builder().type(TokenType.VAR).value("a").build();
        VarNode node1 = VarNode.builder().token(varToken)
                .postIncrDecrToken(Token.builder().type(TokenType.DOUBLE_PLUS).build())
                .build();
        VarNode node2 = VarNode.builder().token(varToken)
                .postIncrDecrToken(Token.builder().type(TokenType.DOUBLE_MINUS).build())
                .build();
        assertNotEquals(node1, node2);
    }

    @Test
    void testBuild_NullToken() {
        assertThrows(BuildVarNodeException.class, () -> VarNode.builder().build());
    }


}