package tbl.eval.ast;

import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.number.Number;
import tbl.eval.number.NumberType;
import tbl.eval.token.Token;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NumberNodeTest {

    @Mock
    private Token token;
    @Mock
    private Number number;

    @Mock
    TreeVisitor visitor;

    @InjectMocks
    private NumberNode node;

    @BeforeEach
    void init() throws Exception {
        when(visitor.visitNumberNode(any(NumberNode.class))).thenReturn(new Number(NumberType.LONG, 1L));
    }

    @Test
    public void testAccept() {
        Number number = node.accept(visitor);
        assertNotNull(number);
    }
}