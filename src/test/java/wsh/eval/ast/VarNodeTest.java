package wsh.eval.ast;

import org.junit.jupiter.api.BeforeEach;
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



}