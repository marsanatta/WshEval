package tbl.eval;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tbl.eval.ast.AssignOpNode;
import tbl.eval.ast.BinaryOpNode;
import tbl.eval.ast.NumberNode;
import tbl.eval.ast.TreeNode;
import tbl.eval.ast.UnaryOpNode;
import tbl.eval.ast.VarNode;
import tbl.eval.exceptions.UnknownTokenTypeException;
import tbl.eval.number.NumberType;
import tbl.eval.token.Token;
import tbl.eval.token.TokenType;
import tbl.eval.variable.VariableStore;
import tbl.eval.number.Number;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterpreterTest {

    @Mock
    private Lexer lexer;
    @Mock
    private Parser parser;
    @Mock
    private VariableStore varStore;

    private Interpreter interpreter;

    @BeforeEach
    void setUp() {
        interpreter = Interpreter.builder()
                .lexer(lexer)
                .parser(parser)
                .varStore(varStore)
                .build();
    }

    @Test
    void testVisitBinaryOpNode_PLUS() throws Exception {
        TreeNode left = mock();
        TreeNode right = mock();
        Number leftNumber = mock();
        Number rightNumber = mock();
        Number resultNumber = new Number(NumberType.LONG, 1L);
        when(left.accept(eq(interpreter))).thenReturn(leftNumber);
        when(right.accept(eq(interpreter))).thenReturn(rightNumber);
        when(leftNumber.add(eq(rightNumber))).thenReturn(resultNumber);

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.PLUS).build()).right(right).build();
        assertEquals(resultNumber, (interpreter.visitBinaryOpNode(node)));
    }

    @Test
    void testVisitBinaryOpNode_MINUS() throws Exception {
        TreeNode left = mock();
        TreeNode right = mock();
        Number leftNumber = mock();
        Number rightNumber = mock();
        Number resultNumber = new Number(NumberType.LONG, 1L);
        when(left.accept(eq(interpreter))).thenReturn(leftNumber);
        when(right.accept(eq(interpreter))).thenReturn(rightNumber);
        when(leftNumber.subtract(eq(rightNumber))).thenReturn(resultNumber);

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.MINUS).build()).right(right).build();
        assertEquals(resultNumber, (interpreter.visitBinaryOpNode(node)));
    }

    @Test
    void testVisitBinaryOpNode_MUL() throws Exception {
        TreeNode left = mock();
        TreeNode right = mock();
        Number leftNumber = mock();
        Number rightNumber = mock();
        Number resultNumber = new Number(NumberType.LONG, 1L);
        when(left.accept(eq(interpreter))).thenReturn(leftNumber);
        when(right.accept(eq(interpreter))).thenReturn(rightNumber);
        when(leftNumber.multiply(eq(rightNumber))).thenReturn(resultNumber);

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.MUL).build()).right(right).build();
        assertEquals(resultNumber, (interpreter.visitBinaryOpNode(node)));
    }

    @Test
    void testVisitBinaryOpNode_DIV() throws Exception {
        TreeNode left = mock();
        TreeNode right = mock();
        Number leftNumber = mock();
        Number rightNumber = mock();
        Number resultNumber = new Number(NumberType.LONG, 1L);
        when(left.accept(eq(interpreter))).thenReturn(leftNumber);
        when(right.accept(eq(interpreter))).thenReturn(rightNumber);
        when(leftNumber.divide(eq(rightNumber))).thenReturn(resultNumber);

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.DIV).build()).right(right).build();
        assertEquals(resultNumber, (interpreter.visitBinaryOpNode(node)));
    }

    @Test
    void testVisitBinaryOpNode_REM() throws Exception {
        TreeNode left = mock();
        TreeNode right = mock();
        Number leftNumber = mock();
        Number rightNumber = mock();
        Number resultNumber = new Number(NumberType.LONG, 1L);
        when(left.accept(eq(interpreter))).thenReturn(leftNumber);
        when(right.accept(eq(interpreter))).thenReturn(rightNumber);
        when(leftNumber.remainder(eq(rightNumber))).thenReturn(resultNumber);

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.REM).build()).right(right).build();
        assertEquals(resultNumber, (interpreter.visitBinaryOpNode(node)));
    }

    @Test
    void testVisitBinaryOpNode_Unknown() {
        TreeNode left = mock();
        TreeNode right = mock();

        BinaryOpNode node = BinaryOpNode.builder().left(left).op(Token.builder().type(TokenType.DOUBLE_MINUS).build()).right(right).build();
        assertThrows(UnknownTokenTypeException.class, () -> interpreter.visitBinaryOpNode(node));
    }

    @Test
    void testVisitUnaryOpNode_PLUS() throws Exception {
        TreeNode expr = mock();
        Number exprNumber = new Number(NumberType.LONG, 1L);
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);
        UnaryOpNode node = UnaryOpNode.builder().op(Token.builder().type(TokenType.PLUS).build()).expr(expr).build();
        assertEquals(exprNumber, interpreter.visitUnaryOpNode(node));
    }

    @Test
    void testVisitUnaryOpNode_MINUS() throws Exception {
        TreeNode expr = mock();
        Number exprNumber = new Number(NumberType.LONG, 1L);
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);
        UnaryOpNode node = UnaryOpNode.builder().op(Token.builder().type(TokenType.MINUS).build()).expr(expr).build();
        assertEquals(new Number(NumberType.LONG, -1L), interpreter.visitUnaryOpNode(node));
    }

    @Test
    void testVisitUnaryOpNode_Unknown() {
        TreeNode expr = mock();
        UnaryOpNode node = UnaryOpNode.builder().op(Token.builder().type(TokenType.DOUBLE_PLUS).build()).expr(expr).build();
        assertThrows(UnknownTokenTypeException.class, () -> interpreter.visitUnaryOpNode(node));
    }

    @Test
    void testVisitAssignOpNode_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();

        TreeNode expr = mock();
        Number exprNumber = new Number(NumberType.LONG, 2L);
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.ASSIGN).build()).right(expr).build();
        assertEquals(exprNumber, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(exprNumber));
    }

    @Test
    void testVisitAssignOpNode_ADD_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        Number newVarValue = new Number(NumberType.LONG, 1L);
        when(varValue.add(eq(exprNumber))).thenReturn(newVarValue);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.ADD_ASSIGN).build()).right(expr).build();
        assertEquals(newVarValue, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testVisitAssignOpNode_SUB_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        Number newVarValue = new Number(NumberType.LONG, 1L);
        when(varValue.subtract(eq(exprNumber))).thenReturn(newVarValue);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.SUB_ASSIGN).build()).right(expr).build();
        assertEquals(newVarValue, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testVisitAssignOpNode_MUL_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        Number newVarValue = new Number(NumberType.LONG, 1L);
        when(varValue.multiply(eq(exprNumber))).thenReturn(newVarValue);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.MUL_ASSIGN).build()).right(expr).build();
        assertEquals(newVarValue, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testVisitAssignOpNode_DIV_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        Number newVarValue = new Number(NumberType.LONG, 1L);
        when(varValue.divide(eq(exprNumber))).thenReturn(newVarValue);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.DIV_ASSIGN).build()).right(expr).build();
        assertEquals(newVarValue, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testVisitAssignOpNode_REM_ASSIGN() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        Number newVarValue = new Number(NumberType.LONG, 1L);
        when(varValue.remainder(eq(exprNumber))).thenReturn(newVarValue);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.REM_ASSIGN).build()).right(expr).build();
        assertEquals(newVarValue, interpreter.visitAssignOpNode(node));
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testVisitAssignOpNode_Unknown() throws Exception {
        String varName = "a";
        VarNode varNode = VarNode.builder().token(mock()).varName(varName).build();

        TreeNode expr = mock();
        Number exprNumber = mock();
        when(expr.accept(eq(interpreter))).thenReturn(exprNumber);

        AssignOpNode node = AssignOpNode.builder().left(varNode).op(Token.builder().type(TokenType.DOUBLE_PLUS).build()).right(expr).build();
        assertThrows(UnknownTokenTypeException.class, () -> interpreter.visitAssignOpNode(node));
    }

    @Test
    void testVisitNumberNode() {
        Number num = new Number(NumberType.LONG, 1L);
        NumberNode node = new NumberNode(Token.builder().type(TokenType.NUM).value(num).build());
        assertEquals(num, interpreter.visitNumberNode(node));
    }

    @Test
    void visitVarNode() throws Exception {
        String varName = "a";
        VarNode node = VarNode.builder().token(mock()).varName(varName).build();
        Number varValue = new Number(NumberType.LONG, 1L);
        when(varStore.get(eq(varName))).thenReturn(varValue);

        assertEquals(varValue, interpreter.visitVarNode(node));
    }

    @Test
    void visitVarNode_PreIncrOp() throws Exception {
        String varName = "a";
        VarNode node = VarNode.builder().token(mock()).varName(varName)
                .preIncrDecrToken(Optional.of(Token.builder().type(TokenType.DOUBLE_PLUS).build()))
                .build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        Number newVarValue = new Number(NumberType.LONG, 2L);
        when(varValue.add(eq(new Number(NumberType.LONG, 1L)))).thenReturn(newVarValue);

        assertEquals(newVarValue, interpreter.visitVarNode(node)); // eval new variable value
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void visitVarNode_PreDecrOp() throws Exception {
        String varName = "a";
        VarNode node = VarNode.builder().token(mock()).varName(varName)
                .preIncrDecrToken(Optional.of(Token.builder().type(TokenType.DOUBLE_MINUS).build()))
                .build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        Number newVarValue = new Number(NumberType.LONG, 2L);
        when(varValue.subtract(eq(new Number(NumberType.LONG, 1L)))).thenReturn(newVarValue);

        assertEquals(newVarValue, interpreter.visitVarNode(node)); // eval new variable value
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void visitVarNode_PostIncrOp() throws Exception {
        String varName = "a";
        VarNode node = VarNode.builder().token(mock()).varName(varName)
                .postIncrDecrToken(Optional.of(Token.builder().type(TokenType.DOUBLE_PLUS).build()))
                .build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        Number newVarValue = new Number(NumberType.LONG, 2L);
        when(varValue.add(eq(new Number(NumberType.LONG, 1L)))).thenReturn(newVarValue);

        assertEquals(varValue, interpreter.visitVarNode(node)); // original value
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void visitVarNode_PostDecrOp() throws Exception {
        String varName = "a";
        VarNode node = VarNode.builder().token(mock()).varName(varName)
                .postIncrDecrToken(Optional.of(Token.builder().type(TokenType.DOUBLE_MINUS).build()))
                .build();
        Number varValue = mock();
        when(varStore.get(eq(varName))).thenReturn(varValue);

        Number newVarValue = new Number(NumberType.LONG, 2L);
        when(varValue.subtract(eq(new Number(NumberType.LONG, 1L)))).thenReturn(newVarValue);

        assertEquals(varValue, interpreter.visitVarNode(node)); // original value
        verify(varStore, times(1)).set(eq(varName), eq(newVarValue));
    }

    @Test
    void testInterpret() throws Exception {
        TreeNode node = mock();
        when(parser.parse()).thenReturn(node);
        Number res = new Number(NumberType.LONG, 1L);
        when(node.accept(eq(interpreter))).thenReturn(res);

        String text = "a=1";
        assertEquals(res, interpreter.interpret(text));
        verify(lexer, times(1)).consume(eq(text));
    }

    @Test
    void testInterpret_NullTree() throws Exception {
        when(parser.parse()).thenReturn(null);
        assertNull(interpreter.interpret("a=1"));
    }
}