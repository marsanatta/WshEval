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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UnaryOpNodeTest {

    @Mock
    private Token op;

    @Mock
    private TreeNode expr;

    @InjectMocks
    private UnaryOpNode node;

    @Mock
    private TreeVisitor visitor;

    @BeforeEach
    void init() throws Exception {
        when(visitor.visitUnaryOpNode(any(UnaryOpNode.class))).thenReturn(new Number(NumberType.LONG, 1L));
    }

    @Test
    public void testAccept() throws Exception {
        Number number = node.accept(visitor);
        assertNotNull(number);
    }


}